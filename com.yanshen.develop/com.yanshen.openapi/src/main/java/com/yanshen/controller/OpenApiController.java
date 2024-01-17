package com.yanshen.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server")
public class OpenApiController {

    @RequestMapping("/r")


    public String getMsg(){
        return "this msg from application->openapi !";
    }
}
