package com.yanshen.dev.controller;

import com.alibaba.fastjson.JSONObject;

import com.yanshen.dev.annotation.AnonymousGetMapping;
import com.yanshen.dev.rocketmq.CommandType;
import com.yanshen.dev.rocketmq.RocketProducer;
import lombok.Data;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author luojiancheng
 * @description
 * @date 2022/06/05
 */
@RestController
@RequestMapping("/rocket")
public class RocketMqController {
    @Resource
    private RocketProducer rocketMQProducer;

    @ResponseBody
    @AnonymousGetMapping("/send")
    public void send(){
        Test test=new Test();
        test.setAge("24");
        test.setName("lee");
        test.setTel("10010");
        rocketMQProducer.sendMsg(CommandType.CREATE_MSG.getType(), (JSONObject) JSONObject.toJSON(test));
    }

    @AnonymousGetMapping("/asyncSend")
    public void asyncSend(){
        Test test=new Test();
        test.setAge("24");
        test.setName("lee");
        test.setTel("10010");
        rocketMQProducer.asyncSendMsg(CommandType.CREATE_MSG.getType(), (JSONObject) JSONObject.toJSON(test));
    }

    @Data
    public  class Test{
        private String name;
        private String tel;
        private String age;
        private String agea;
        private String agev;
        private String agec;
        private String s;
    }

}
