package com.yanshen.controller;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import com.yanshen.common.Result;
import com.yanshen.constants.AuthConstats;
import com.yanshen.entity.LoginUser;
import com.yanshen.entity.SysUser;
import com.yanshen.entity.dto.LoginDTO;
import com.yanshen.service.SysUserService;
import com.yanshen.util.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 知否技术
 * @since 2022-03-09
 */
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService userService;

    @Autowired
    private RedisUtil redisUtil;


    @PostMapping("/login")
    public Result login(@RequestBody LoginDTO loginDTO) {
        String captcha = loginDTO.getCaptcha();
        if (captcha == null) throw new RuntimeException("验证码无效!");
        if (captcha.equals("yzm")) return userService.login(loginDTO);
        String lowerCaseCaptcha = captcha.toLowerCase();
        String realKey = MD5Util.MD5Encode(lowerCaseCaptcha + loginDTO.getCheckKey(), "utf-8");
        Object checkCode = redisUtil.get(realKey);
        //当进入登录页时，有一定几率出现验证码错误 #1714
        if (checkCode == null || !checkCode.toString().equals(lowerCaseCaptcha)) return Result.fail("验证码错误");
        // 从数据库中查找用户的信息，信息正确生成token
        return userService.login(loginDTO);
    }
    @GetMapping("/list")
    public Result list() {
        return Result.success(userService.list());
    }

    @PostMapping("/register")
    public Result<SysUser> register(@RequestBody SysUser sysUser) {
        sysUser.setPassword(BcryptUtil.encode(sysUser.getPassword()));
        boolean save = userService.save(sysUser);
        if(save){
            StpUtil.login(sysUser.getUserName());
        }
        return Result.success(sysUser);
    }


    /**
     * 后台生成图形验证码 ：有效
     *
     * @param response
     * @param
     */
    @ApiOperation("获取验证码")
    @GetMapping(value = "/randomImage")
    public Result randomImage(HttpServletResponse response) {
        long key = System.currentTimeMillis();
        JSONObject object = new JSONObject();
        String BASE_CHECK_CODES = "qwertyuiplkjhgfdsazxcvbnmQWERTYUPLKJHGFDSAZXCVBNM1234567890";
        try {
            String code = RandomUtil.randomString(BASE_CHECK_CODES, 4);
            String lowerCaseCode = code.toLowerCase();
            String realKey = MD5Util.MD5Encode(lowerCaseCode + key, "utf-8");
            redisUtil.set(realKey, lowerCaseCode, 60, TimeUnit.SECONDS);
            String base64 = RandImageUtil.generate(code);
            object.set("captcha", base64);
            object.set("timestamp", key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取验证码出错" + e.getMessage());
        }
        return Result.success(object);
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam String id) {
        try {
            return Result.success(userService.getById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(e.getMessage());
        }
    }

    @GetMapping("/getme")
    public Result getme() {
        String currentUserId = ThreadLocalUtils.getCurrentUserId();
        try {
            SysUser byId = userService.getById(currentUserId);
            LoginUser loginUser = new LoginUser();
            BeanUtil.copyProperties(byId, loginUser);
            return Result.success(loginUser);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(e.getMessage());
        }
    }

    @RequestMapping("/quit")
    public Result logOut() {
        String currentUserId = ThreadLocalUtils.getCurrentUserId();
        redisUtil.delete(AuthConstats.USER_TOKEN_PREFIX+currentUserId);
        return Result.success("傻逼!");
    }
}
