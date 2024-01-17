package com.yanshen.controller;

import com.yanshen.base.Result;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {


    @RequestMapping("/load")
    public Result getMyOrder(Authentication authentication){

        return Result.success("获取成功!");
    }
}
