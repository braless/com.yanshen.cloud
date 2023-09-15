package com.yanshen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
}