package com.yanshen.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yanshen.common.CacheTime;
import com.yanshen.common.Result;
import com.yanshen.entity.LoginUser;
import com.yanshen.entity.SysUser;
import com.yanshen.entity.reqValidate.LoginDTO;
import com.yanshen.mapper.UserMapper;
import com.yanshen.service.SysUserService;
import com.yanshen.util.BcryptUtil;
import com.yanshen.util.JwtUtil;
import com.yanshen.util.RedisUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.yanshen.constants.AuthConstats;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 知否技术
 * @since 2022-03-09
 */
@Service
@Slf4j
public class SysUserServiceImpl extends ServiceImpl<UserMapper, SysUser> implements SysUserService {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public Result login(LoginDTO loginDTO) {
        // 先从数据库查询
        SysUser sysUser = this.getOne(new QueryWrapper<SysUser>().eq("user_name", loginDTO.getUserName()));
        if (null == sysUser) {
            Result.fail("用户不存在");
        }
        if (!BcryptUtil.match(loginDTO.getPassword(), sysUser.getPassword())) {
            return Result.fail("密码错误");
        }
        Map<String,Object> userMap= BeanUtil.beanToMap(sysUser);
        String jwtToken = jwtUtil.createJwtToken(sysUser.getId().toString(), CacheTime.JWT_EXPIRE_SECONDS/60);
        //String jwtToken = jwtUtil.createJwtToken(userMap, EXPIRE_SECONDS);
        long exp =jwtUtil.getExpTime(jwtToken);
        String expiredTime= DateUtil.format(new Date(exp),"yyyy-MM-dd HH:mm:ss");
        String loginTime =DateUtil.format(new Date(),"yyyy-MM-dd HH:mm:ss");
        JSONObject object =new JSONObject();
        object.set("expire_at",expiredTime);
        object.set("token",jwtToken);
        LoginUser loginUser =new LoginUser();
        BeanUtils.copyProperties(sysUser,loginUser);
        object.set("userInfo", loginUser);
        loginUser.setToken(jwtToken);
        loginUser.setExpireAt(expiredTime);
        loginUser.setLoginTime(loginTime);
        redisUtil.set(AuthConstats.USER_TOKEN_PREFIX + loginUser.getId(), loginUser, CacheTime.JWT_REDDIS_EXPIRE, TimeUnit.SECONDS);
        log.info("用户: {},登录成功!,token过期时间:{}",loginUser.getUserName(),expiredTime);
        return Result.success(object);
    }
}
