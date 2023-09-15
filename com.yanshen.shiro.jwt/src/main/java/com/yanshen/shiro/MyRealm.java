package com.yanshen.shiro;

/**
 * @Desc:
 * @Author: 知否技术
 * @date: 下午10:33 2022/3/17
 */
import com.yanshen.entity.User;
import com.yanshen.util.JwtUtil;
import com.yanshen.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 限定这个realm只能处理JwtToken
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 授权(授权部分这里就省略了)
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        // 获取到用户名，查询用户权限
        return null;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken){
        // 获取token信息
        String token = (String) authenticationToken.getCredentials();
        // 校验token：未校验通过或者已过期
        if (!jwtUtil.verifyToken(token) || jwtUtil.isExpire(token)) {
            throw new AuthenticationException("token已失效，请重新登录");
        }
        // 用户信息
        User user = (User) redisUtil.get("user_token:" + token);
        if (null == user) {
            log.error("登录状态失效!");
            throw new AuthenticationException("登录状态失效");
        }
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, token, this.getName());
        return simpleAuthenticationInfo;
    }
}
