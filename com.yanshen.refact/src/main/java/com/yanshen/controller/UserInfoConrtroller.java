package com.yanshen.controller;


import com.yanshen.base.ApiResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserInfoConrtroller {

    @RequestMapping("/info")
    public ApiResult info(){
        return ApiResult.success();
    }
}
