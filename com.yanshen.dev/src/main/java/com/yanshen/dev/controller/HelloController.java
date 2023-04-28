package com.yanshen.dev.controller;


import com.yanshen.dev.service.HelloService;
import com.yanshen.dev.service.ZhuRuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HelloController {

    @Autowired
    HelloService service;

    @Autowired
    @Qualifier(value = "impl2")
    private ZhuRuService zhuRuService;

    @RequestMapping("/hello")
    public String hello(HttpServletRequest request, @RequestParam(value = "name", required = false, defaultValue = "springboot-thymeleaf") String name) {
        request.setAttribute("name", name);
        request.setAttribute("user", "你妈的!");
        return "hello";
    }

    @RequestMapping("/")
    public String blank(HttpServletRequest request, @RequestParam(value = "name", required = false, defaultValue = "springboot-thymeleaf") String name) {
        request.setAttribute("user", "你妈的!");
        String s = service.doHello();
        System.out.println(s);
        return "index";
    }

    @RequestMapping("/test")
    public String getName() {
        return zhuRuService.getName();
    }
}

