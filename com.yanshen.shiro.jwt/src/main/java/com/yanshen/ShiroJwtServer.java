package com.yanshen;

import com.yanshen.common.TokenFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-15 11:51
 * @Description:
 * @Location: com.yanshen
 * @Project: Default (Template) Project
 */
@SpringBootApplication
@MapperScan("com.yanshen.mapper")
public class ShiroJwtServer {
    public static void main(String[] args) {
        SpringApplication.run(ShiroJwtServer.class);
        System.out.println("Hello world!");
    }

    @Bean
    public FilterRegistrationBean tokenFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new TokenFilter());// 添加过滤器
        registration.addUrlPatterns("/*");// 设置过滤路径，/*所有路径
        registration.setName("TokenFilter");// 设置优先级
        registration.setOrder(5);// 设置优先级
        return registration;
    }
}