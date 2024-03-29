package com.yanshen.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @Desc:
 * 通过这个类将 string 的 token 转成 AuthenticationToken，shiro 才能接收
 * 由于Shiro不能识别字符串的token，所以需要对其进行一下封装
 * @Author: 知否技术
 * @date: 下午10:30 2022/3/17
 */
public class JwtToken implements AuthenticationToken {
    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }
    @Override
    public Object getCredentials() {
        return token;
    }
}
