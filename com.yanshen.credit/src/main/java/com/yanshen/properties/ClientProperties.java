package com.yanshen.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Classname UserClientProperties
 * @Description
 * @Date 2022/8/29 15:03
 * @Author wanglei
 */
@Data
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "auth.cloud")
public class ClientProperties {

    private List<ClientInfoModel> client;

    @Data
    public static class ClientInfoModel {

        /**
         * 客户端ID
         */
        private String client_id;

        private String client_secret;

        private String prefix;

        /**
         * 授权范围标识
         */
        private String scopes;

        /**
         * 授权类型，支持同时多种授权类型
         * authorization_code:授权模式
         * implicit:隐式授权模式(简化模式)
         * password:密码模式
         * client_credentials:客户端模式
         * refresh_token:刷新令牌模式
         */
        private String[] authorizedGrantTypes;

        /**
         * token有效时间
         */
        private Integer accessTokenValiditySeconds;

        /**
         * refreshToken有效时间
         */
        private Integer refreshTokenValiditySeconds;

    }
}