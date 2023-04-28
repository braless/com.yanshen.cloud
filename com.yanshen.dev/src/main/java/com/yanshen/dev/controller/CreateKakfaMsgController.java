package com.yanshen.dev.controller;

import com.yanshen.dev.kafka.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

/**
 * @Auther: cyc
 * @Date: 2023/3/7 19:27
 * @Description:
 */
@RestController
@RequestMapping("/kafka")
@Slf4j
public class CreateKakfaMsgController {
    @Autowired
    KafkaProducer kafkaProducer;
    @RequestMapping("/do")
    public int create() throws ExecutionException, InterruptedException {

        log.info("开始发送卡夫卡消息");
        for (int i = 0; i <6 ; i++) {
            kafkaProducer.sendKafka("test","---"+i);
        }
        return 1;
    }
}
