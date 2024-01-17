package com.yanshen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.javatool.canal.client.client.SimpleCanalClient;

@Configuration
public class CanalConfig {

    private static String addr="120.46.171.189:5019";
    private static int port=5019;
    private static String userName="weibo";
    private static String passWord="123456";
    private static String destination="example";

    public CanalConfig() {
        System.out.println("Canal客户端启动!");
    }

    @Bean
    public SimpleCanalClient build(){
        return SimpleCanalClient.builder()
                .userName(userName)
                .password(passWord)
                .port(port)
                .destination(destination)
                .hostname(addr).build();
    }
}
