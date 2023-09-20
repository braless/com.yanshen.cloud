package com.yanshen.constants;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-18 17:29
 * @Description:
 * @Location: com.yanshen.constants
 * @Project: com.yanshen.cloud
 */
public interface AuthConstats {
    /**
     * 认证信息Http请求头
     */
    String JWT_TOKEN_HEADER = "Authorization";


    String ACCESS_TOKEN = "Access-Token";

    /**
     * JWT令牌前缀
     */
    String JWT_TOKEN_PREFIX = "Bearer ";

    String USER_TOKEN_PREFIX="user_token:";
}
