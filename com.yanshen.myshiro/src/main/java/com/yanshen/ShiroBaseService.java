package com.yanshen;

import com.yanshen.base.ExceptionFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude ={
        RedisAutoConfiguration.class,
        RedisRepositoriesAutoConfiguration.class
} )

@MapperScan("com.yanshen.mapper")
public class ShiroBaseService {
    public static void main(String[] args) {
        SpringApplication.run(ShiroBaseService.class);
    }
    //@Bean
    public FilterRegistrationBean<ExceptionFilter> testFilterRegistration() {
        FilterRegistrationBean<ExceptionFilter> registration = new FilterRegistrationBean<ExceptionFilter>();
        registration.setFilter(new ExceptionFilter());// 添加过滤器
        registration.addUrlPatterns("/*");// 设置过滤路径，/*所有路径
        registration.setName("ExceptionFilter");// 设置优先级
        registration.setOrder(1);// 设置优先级
        return registration;
    }
}