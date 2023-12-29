package com.yanshen.controller;


import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.yanshen.base.Result;
import com.yanshen.dto.AuthUserDto;
import com.yanshen.oauth2.AuthService;
import com.yanshen.oauth2.Oauth2TokenDto;
import com.yanshen.oauth2.service.SysUserClient;
import com.yanshen.properties.UserClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/oauth")
@Slf4j
public class AuthController {

    @Autowired
    private UserClientProperties userClientProperties;

    @Autowired
    AuthService authService;

    @Autowired
    SysUserClient sysUserClient;


    @RequestMapping("/token")
    public Result login(@Validated @RequestBody AuthUserDto authUser, HttpServletRequest request) throws Exception {
        Map<String, String> params = new HashMap<>();
        params.put("client_id", userClientProperties.getBase().getClient_id());
        params.put("client_secret", userClientProperties.getBase().getClient_secret());
        params.put("grant_type", userClientProperties.getBase().getGrant_type());
        params.put("username", authUser.getUsername());
        params.put("password", authUser.getPassword());
        String entCode = authUser.getPassword();

        AuthUserDto userDto =sysUserClient.loadUserByUsername(authUser.getUsername());
        if (userDto==null){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            // 加密
            String encodedPassword = passwordEncoder.encode(authUser.getPassword().trim());
            authUser.setPassword(encodedPassword);
            Snowflake snowflake = IdUtil.createSnowflake(1, 1);
// 有两种返回值类型
            long id = snowflake.nextId();
            authUser.setId(id);
            authUser.setUserCode(entCode);
            authUser.setEnabled(true);
            sysUserClient.saveUser(authUser);
            log.info("用户保存成功!: {}",authUser.toString());

        }
        Result<Oauth2TokenDto> oauth2TokenDtoResult = authService.postAccessToken(request, params);
        return oauth2TokenDtoResult;
    }
}
