package com.yanshen.dev.service;

import org.springframework.stereotype.Service;

/**
 * <h3>spring-cloud</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2022-03-17 15:36
 **/
@Service
public class HelloService {

    public String doHello(){

        return "HelloWorld";
    }
}
