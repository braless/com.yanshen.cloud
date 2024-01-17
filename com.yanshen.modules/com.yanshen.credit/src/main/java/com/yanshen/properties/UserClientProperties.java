package com.yanshen.properties;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

/**
 * @Classname UserClientProperties
 * @Description
 * @Date 2022/8/29 15:03
 * @Author wanglei
 */
@Data
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = "credit.client.user")
public class UserClientProperties {

    private ClientInfoModel base;

    private ClientInfoModel portal;

    private ClientInfoModel gateway;

    @Data
    public static class ClientInfoModel {

        private String client_id;

        private String client_secret;

        private String prefix;

        private String grant_type;

    }

}