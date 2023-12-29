package com.yanshen.oauth2;


import lombok.*;

import java.io.Serializable;

/**
 * Oauth2获取Token返回信息封装
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Oauth2TokenDto implements Serializable {

    /**
     * 访问令牌
     */
    private String token;

    /**
     * 刷令牌
     */
    private String refreshToken;

    /**
     * 访问令牌头前缀
     */
    private String tokenHead;

    /**
     * 有效时间（秒）
     */
    private int expiresIn;

}