package com.yanshen.weibo.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rpc")
public class RpcController {
    @RequestMapping("/l")
    public String controller() throws InterruptedException {
        Thread.sleep(3000);
        return "hello this is weibo !";
    }
}
