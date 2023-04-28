package com.yanshen.author.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h3>spring-cloud</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2022-03-01 15:01
 **/
@RestController
@RequestMapping("/auth")
public class AuthController {

    @RequestMapping("/do")
    public String auth(){
        return "this is ok";
    }
}
