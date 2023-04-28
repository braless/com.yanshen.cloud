package com.yanshen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.yanshen.mapper")
public class Generator {

    public static void main(String[] args) {
        SpringApplication.run(Generator.class, args);
    }

}
