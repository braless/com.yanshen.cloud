package com.yanshen.shiro;

/**
 * @Desc:
 * @Author: 知否技术
 * @date: 下午10:33 2022/3/17
 */

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.yanshen.api.CommonAPI;
import com.yanshen.common.CacheTime;
import com.yanshen.constants.AuthConstats;
import com.yanshen.entity.LoginUser;
import com.yanshen.util.JwtUtil;
import com.yanshen.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class MyRealm extends AuthorizingRealm {

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private JwtUtil jwtUtil;

    @Lazy
    @Resource
    private CommonAPI commonAPI;

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

        //获取登录用户名
        String userName = (String) principals.getPrimaryPrincipal();
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        // 设置用户拥有的角色集合，比如“admin,test”
        Set<String> roleSet = commonAPI.queryUserRoles(userName);
//        List<Map<String, Object>> powerList = loginService.getUserPower(userName);
//        System.out.println(powerList.toString());
//        for (Map<String, Object> powerMap : powerList) {
//            //添加角色
//            simpleAuthorizationInfo.addRole(String.valueOf(powerMap.get("roleName")));
//            //添加权限
//            simpleAuthorizationInfo.addStringPermission(String.valueOf(powerMap.get("permissionsName")));
//        }
        simpleAuthorizationInfo.setRoles(roleSet);
        return simpleAuthorizationInfo;
        // 获取到用户名，查询用户权限
        //return null;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) {
        log.warn("===============Shiro身份认证开始============doGetAuthenticationInfo==========");
        // 获取token信息
        String token = (String) authenticationToken.getCredentials();
        if (StrUtil.isEmpty(token)) {
            log.warn("————————身份认证失败——————————");
            throw new AuthenticationException("token为空!");
        }
        //检查用户信息
        LoginUser loginUser = this.checkTokenAlive(token);
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(loginUser, token, this.getName());
        return simpleAuthenticationInfo;
    }

    /**
     *
     * @param token
     * @return
     */
    public LoginUser checkTokenAlive(String token) {
        // 校验token：未校验通过或者已过期   注释是因为 后面有  refreshToken 续期
//        if (!jwtUtil.verifyToken(token) || jwtUtil.isExpire(token)) {
//            throw new AuthenticationException("Token失效，请重新登录!");
//        }
        String userId= jwtUtil.getTokenClaim(token,"userId");
        if(StrUtil.isEmpty(userId)){
            throw new AuthenticationException("非法用户!");
        }
        // 用户信息
        LoginUser loginUser = (LoginUser) redisUtil.get("user_token:" + userId);
        if (null == loginUser) {
            log.error("用户: {} 登录状态失效!",jwtUtil.getTokenClaim(token,"userId"));
            throw new AuthenticationException("登录状态失效");
        }
        // 校验token是否超时失效 & 或者账号密码是否错误
        if (!refreshToken(token,loginUser)) {
            throw new AuthenticationException("Token失效，请重新登录!");
        }
        if(isSingle(token)){
            throw new AuthenticationException("账号已在别处登录,请重新登录");
        }
        return loginUser;
    }

    /**
     * 刷新token
     *
     * @param token
     * @param loginUser
     * @return
     */
    public boolean refreshToken(String token, LoginUser loginUser) {
        String userId=jwtUtil.getTokenClaim(token,"userId");
        LoginUser cacheToken = (LoginUser) redisUtil.get(AuthConstats.USER_TOKEN_PREFIX + userId);
        if (ObjectUtil.isNotEmpty(cacheToken)) {
            //Header的token过期了,开始给token续期
            boolean ok =jwtUtil.verifyToken(token);
            if (!ok) {
                String newToken = jwtUtil.createJwtToken(loginUser.getId().toString(), CacheTime.JWT_EXPIRE_SECONDS/60);//3600秒 = 60分钟
                String expiredTime = DateUtil.format(new Date(jwtUtil.getExpTime(newToken)), "yyyy-MM-dd HH:mm:ss");
                loginUser.setExpireAt(expiredTime);
                loginUser.setRefreshToken(newToken);
                redisUtil.set("user_token:" + loginUser.getId(), loginUser, CacheTime.JWT_EXPIRE_SECONDS, TimeUnit.SECONDS);// 7200秒  2小时
                log.warn("{},登录刷新token.....,有效期至:{}", loginUser.getUserName(),expiredTime);
            }
            return true;
        }
        //Header的token还没有过期
        return false;
    }


    /**
     * 校验一个账号只能在一处登录
     * @return
     */
    public boolean isSingle(String token){
        String userId=jwtUtil.getTokenClaim(token,"userId");
        LoginUser loginUser =(LoginUser) redisUtil.get(AuthConstats.USER_TOKEN_PREFIX +userId);
        if(!token.equals(loginUser.getToken())){
            log.warn("账号在别处登录了,本次登录下线!,需要重新登录!");
            return true;
        }
          return false;
    }
}
