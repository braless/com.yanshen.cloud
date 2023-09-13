package com.yanshen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;

@SpringBootApplication(exclude ={
        RedisAutoConfiguration.class,
        RedisRepositoriesAutoConfiguration.class
} )

@MapperScan("com.yanshen.mapper")
public class ShiroBaseService {
    public static void main(String[] args) {
        SpringApplication.run(ShiroBaseService.class);
    }
}