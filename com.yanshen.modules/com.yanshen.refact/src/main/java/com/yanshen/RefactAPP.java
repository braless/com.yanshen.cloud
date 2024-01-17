package com.yanshen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yanshen.mapper")
public class RefactAPP {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        SpringApplication.run(RefactAPP.class, args);
    }
}