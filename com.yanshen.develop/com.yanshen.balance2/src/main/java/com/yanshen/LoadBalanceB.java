package com.yanshen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(exclude = RedisAutoConfiguration.class)
//@EnableEurekaClient
@EnableFeignClients
@RefreshScope
@EnableDiscoveryClient
public class LoadBalanceB {

    public static void main(String[] args) {
        SpringApplication.run(LoadBalanceB.class, args);
    }

}
