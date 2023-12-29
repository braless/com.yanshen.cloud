package com.yanshen.aspect;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.yanshen.common.CacheTime;
import com.yanshen.constants.AuthConstats;
import com.yanshen.entity.LoginUser;
import com.yanshen.util.JwtUtil;
import com.yanshen.util.RedisUtil;
import com.yanshen.util.SpringContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-19 11:18
 * @Description:
 * @Location: com.yanshen.aspect
 * @Project: com.yanshen.cloud
 */

@Aspect
@Component
@Slf4j
public class WebAuth {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JwtUtil jwtUtil;


    @Pointcut("@annotation(com.yanshen.aspect.annotation.WebAuth)")
    public void pointCut() {
    }


    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
        String accessToken =request.getHeader(AuthConstats.ACCESS_TOKEN);
        if (accessToken != null) {
//            if (!jwtUtil.verifyToken(token)) {
//                throw new AuthenticationException("token非法无效!");
//            }
            // 查询用户信息
            log.debug("———校验token是否有效————checkUserTokenIsEffect——————— "+ accessToken);
            // 用户信息
            String userId=jwtUtil.getTokenClaim(accessToken,"userId");
            LoginUser loginUser = (LoginUser) redisUtil.get("user_token:" + userId);
            if (loginUser == null) {
                throw new AuthenticationException("用户不存在!");
            }
            // 校验token是否超时失效 & 或者账号密码是否错误
            if (!refreshToken(accessToken,loginUser)) {
                throw new AuthenticationException("Token失效，请重新登录!");
            }
        } else {
            //throw new AuthenticationException("当前用户未登录！");
        }
        return pjp.proceed();
    }

    /**
     * 刷新token
     *
     * @param token
     * @param loginUser
     * @return
     */
    public boolean refreshToken(String token, LoginUser loginUser) {
        LoginUser cacheToken = (LoginUser) redisUtil.get("user_token:" + loginUser.getId());
        if (ObjectUtil.isNotEmpty(cacheToken)) {
            //Header的token过期了,开始给token续期
            if (!jwtUtil.verifyToken(token)) {
                String newToken = jwtUtil.createJwtToken(loginUser.getId().toString(), CacheTime.JWT_EXPIRE_SECONDS);//3600秒 = 60分钟
                String expiredTime = DateUtil.format(new Date(jwtUtil.getExpTime(newToken)), "yyyy-MM-dd HH:mm:ss");
                loginUser.setExpireAt(expiredTime);
                redisUtil.set("user_token:" + loginUser.getId(), loginUser, CacheTime.JWT_EXPIRE_SECONDS, TimeUnit.SECONDS);// 7200秒  2小时
                log.info("{},用户在线操作,刷新token.....,有效期:{}", loginUser.getUserName(),expiredTime);
            }else {
                log.info("当前用户:{} ,token有效期: {}",cacheToken.getUserName(),cacheToken.getExpireAt());
            }
            return true;
        }
        //Header的token还没有过期
        return false;
    }

}
