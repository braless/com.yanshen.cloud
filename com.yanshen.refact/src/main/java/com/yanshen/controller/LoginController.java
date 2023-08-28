package com.yanshen.controller;


import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.json.JSONObject;
import com.yanshen.base.ApiResult;
import com.yanshen.base.ResultCode;
import com.yanshen.cache.LoginRedisService;
import com.yanshen.constant.BaseConstant;
import com.yanshen.constant.CommonRedisKey;
import com.yanshen.entity.SysUser;
import com.yanshen.jwt.JwtToken;
import com.yanshen.service.SysUserService;
//import com.yanshen.util.JwtUtil;
import com.yanshen.jwt.JwtUtil;
import com.yanshen.util.PasswordUtil;
import com.yanshen.util.RandImageUtil;
import com.yanshen.util.RedisUtil;
import com.yanshen.vo.LoginSysUserRedisVo;
import com.yanshen.vo.LoginSysUserVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

//https://www.cnblogs.com/swzx-1213/p/12781836.html
@RestController
@RequestMapping("/api")
@Slf4j
public class LoginController {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    SysUserService userService;

    @Autowired
    LoginRedisService loginRedisService;
    private static final String BASE_CHECK_CODES = "qwertyuiplkjhgfdsazxcvbnmQWERTYUPLKJHGFDSAZXCVBNM1234567890";
    @RequestMapping("/login")
    public ApiResult login(@RequestParam("userName") String userName, @RequestParam("passWord") String passWord) {
        ApiResult result =new ApiResult();
        SysUser dbUser = userService.getUserByName(userName);
        //2. 校验用户名或密码是否正确
        String userpassword = PasswordUtil.encrypt(userName, passWord, dbUser.getSalt());
        String syspassword = dbUser.getPassword();
        if (!userpassword.equals(syspassword)){
            return ApiResult.failed(ResultCode.FAILED,"账号密码错误");
        }
        catchUser(dbUser,result);


        return result;
    }

    /**
     * 后台生成图形验证码 ：有效
     * @param response
     * @param key
     */
    @GetMapping(value = "/randomImage/{key}")
    public ApiResult<String> randomImage(HttpServletResponse response, @PathVariable String key){
        ApiResult result =new ApiResult();
        try {
            String code = RandomUtil.randomString(BASE_CHECK_CODES,4);
            String lowerCaseCode = code.toLowerCase();
            String realKey = DigestUtil.md5Hex(lowerCaseCode+key);
            redisUtil.set(realKey, lowerCaseCode, 60);
            String base64 = RandImageUtil.generate(code);
            result.setData(base64);
        } catch (Exception e) {
            result.setCode(ResultCode.FAILED.getCode());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 用户信息
     *
     * @param sysUser
     * @param result
     * @return
     */
    private ApiResult<JSONObject> catchUser(SysUser sysUser, ApiResult<JSONObject> result) {
        String syspassword = sysUser.getPassword();
        String username = sysUser.getUsername();
        // 生成token
        //String token = JwtUtil.sign(username, syspassword);
        String token =JwtUtil.generateToken(username,sysUser.getSalt(),null);

        LoginSysUserVo sysUserVo =new LoginSysUserVo();
        sysUserVo.setUsername(sysUser.getUsername());

        loginRedisService.cacheLoginInfo(JwtToken.build(token,sysUser.getUsername(),sysUser.getSalt(),60*1000),sysUserVo);
        // 设置token缓存有效时间
        redisUtil.set(BaseConstant.PREFIX_USER_TOKEN + token, token);
        redisUtil.expire(BaseConstant.PREFIX_USER_TOKEN + token, 30*20 / 1000);
        String tokenMd5 = DigestUtils.md5Hex(token);
        String redisKey=String.format(CommonRedisKey.LOGIN_TOKEN, tokenMd5);
        log.info("redisKey:{}",redisKey);
        Object object = redisUtil.set(redisKey,token);
//        // 获取用户部门信息
        JSONObject obj = new JSONObject();
//        List<SysDepart> departs = sysDepartService.queryUserDeparts(sysUser.getId());
//        obj.put("departs", departs);
//        if (departs == null || departs.size() == 0) {
//            obj.put("multi_depart", 0);
//        } else if (departs.size() == 1) {
//            sysUserService.updateUserDepart(username, departs.get(0).getOrgCode());
//            obj.put("multi_depart", 1);
//        } else {
//            //查询当前是否有登录部门
//            // update-begin--Author:wangshuai Date:20200805 for：如果用戶为选择部门，数据库为存在上一次登录部门，则取一条存进去
//            SysUser sysUserById = sysUserService.getById(sysUser.getId());
//            if(oConvertUtils.isEmpty(sysUserById.getOrgCode())){
//                sysUserService.updateUserDepart(username, departs.get(0).getOrgCode());
//            }
//            // update-end--Author:wangshuai Date:20200805 for：如果用戶为选择部门，数据库为存在上一次登录部门，则取一条存进去
//            obj.put("multi_depart", 2);
//        }
        obj.put("token", token);
        obj.put("userInfo", sysUser);
//        obj.put("sysAllDictItems", sysDictService.queryAllDictItems());
//        result.setResult(obj);
//        result.success("登录成功");
        log.info("登录成功,账号:{},token:{}",sysUser.getUsername(),token);
        result.setData(obj);
        return result;
    }
}
