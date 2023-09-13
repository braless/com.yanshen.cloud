package com.yanshen.shiro;

import com.yanshen.dto.SysUserDTO;
import com.yanshen.entity.SysUser;
import com.yanshen.exception.TipException;
import com.yanshen.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Map;

/**
 * @Author : JCccc
 * @CreateTime : 2020/4/24
 * @Description :
 **/
@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private LoginService loginService;

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取登录用户名
        String userName = (String) principalCollection.getPrimaryPrincipal();
        //添加角色和权限
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        List<Map<String, Object>> powerList = loginService.getUserPower(userName);
        System.out.println(powerList.toString());
        for (Map<String, Object> powerMap : powerList) {
            //添加角色
            simpleAuthorizationInfo.addRole(String.valueOf(powerMap.get("roleName")));
            //添加权限
            simpleAuthorizationInfo.addStringPermission(String.valueOf(powerMap.get("permissionsName")));
        }
        return simpleAuthorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (authenticationToken.getPrincipal() == null) {
            return null;
        }
        //获取用户信息
        String token = authenticationToken.getPrincipal().toString();
        //根据用户名去数据库查询用户信息
        SysUserDTO sysUser = this.checkToken(token);
        if (sysUser == null) {
            //这里返回后会报出对应异常
            return null;
        } else {
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            String name = getName();
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(sysUser, token, name);
            return simpleAuthenticationInfo;
        }
    }



    public SysUserDTO checkToken(String token) {
        // 解密获得username，用于和数据库进行对比
        String username = JwtUtil.getUsername(token);
        if (username == null) {
            throw new TipException("token非法无效!");
        }

        // 查询用户信息
        log.debug("———校验token是否有效————checkUserTokenIsEffect——————— " + token);
        SysUser sysUser = loginService.queryUser(username);
        if (sysUser == null) {
            throw new TipException("用户不存在!");
        }
        // 判断用户状态
        if (sysUser.getStatus() != 1) {
            throw new TipException("账号已被锁定,请联系管理员!");
        }
        // 校验token是否超时失效 & 或者账号密码是否错误
//        if (!jwtTokenRefresh(token, username, sysUser.getPassword())) {
//            throw new AuthenticationException("Token失效，请重新登录!");
//        }
        SysUserDTO userDTO = new SysUserDTO();
        BeanUtils.copyProperties(sysUser, userDTO);
        return userDTO;
    }
}