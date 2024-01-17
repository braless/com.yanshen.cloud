package com.yanshen.oauth2.service;

import com.yanshen.base.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.xml.ws.ResponseWrapper;

public interface UserCacheClient {

    /**
     * 清除用户缓存
     * @param token
     * @return
     */
    @PostMapping("/user/cache/cleanUserCache")
    ResponseWrapper cleanUserCache(@RequestParam("token") String token);

    /**
     * token续期
     * @param token
     * @return
     */
    @PostMapping("/user/cache/tokenRenew")
    ResponseWrapper tokenRenew(@RequestParam("token") String token);

    Result initSysUserResource();
}
