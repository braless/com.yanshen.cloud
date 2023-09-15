package com.yanshen.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yanshen.common.Result;
import com.yanshen.entity.User;
import com.yanshen.mapper.UserMapper;
import com.yanshen.service.UserService;
import com.yanshen.util.BcryptUtil;
import com.yanshen.util.JwtUtil;
import com.yanshen.util.RedisUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 知否技术
 * @since 2022-03-09
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private RedisUtil redisUtil;

    private static long EXPIRE_SECONDS=3000;
    @Override
    public Result login(String username, String password) {
        // 先从数据库查询
        User user = this.getOne(new QueryWrapper<User>().eq("username", username));
        if (null == user) {
            Result.fail("用户不存在");
        }
        if (!BcryptUtil.match(password, user.getPassword())) {
            return Result.fail("密码错误");
        }
        String jwtToken = jwtUtil.createJwtToken(user.getId().toString(), EXPIRE_SECONDS);
        long exp =jwtUtil.getExpTime(jwtToken);
        String expiredTime= DateUtil.format(new Date(exp),"yyyy-MM-dd HH:mm:ss");
        JSONObject object =new JSONObject();
        object.set("expire_at",expiredTime);
        object.set("token",jwtToken);
        redisUtil.set("user_token:" + jwtToken, user, 60 * 10, TimeUnit.SECONDS);
        return Result.success(object);
    }
}
