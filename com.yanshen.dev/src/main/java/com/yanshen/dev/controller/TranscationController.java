package com.yanshen.dev.controller;

import com.yanshen.dev.service.TranscationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h3>spring-cloud</h3>
 * <p>事务测试</p>
 *
 * @author : YanChao
 * @date : 2022-03-18 16:51
 **/

@RestController
@RequestMapping("/trs")
public class TranscationController {

    @Autowired
    TranscationService service;

    @RequestMapping("/do")
    public String doTrans(){
        return service.setA();
    }
}
