package com.yanshen.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenFeignLogConfig {

    @Bean
    Logger.Level feignLogLevel(){
        return Logger.Level.FULL;
    }
}
