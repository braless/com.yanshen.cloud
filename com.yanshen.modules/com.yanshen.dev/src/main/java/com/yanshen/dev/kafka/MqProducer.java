package com.yanshen.dev.kafka;

import com.alibaba.fastjson.JSONObject;
import com.yanshen.dev.rocketmq.RocketProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

/**
 * @Auther: cyc
 * @Date: 2023/3/14 17:24
 * @Description:
 */
@Service
public class MqProducer {

    @Value("${kafka.topic.kafeidou_1}")
    private String myTopic;
    @Value("${kafka.topic.kafeidou_2}")
    private String myTopic2;



    @Autowired
    RocketProducer rocketMQProducer;

    @Autowired
    KafkaProducer kafkaProducer;



    public void sendMsg(String msg) throws ExecutionException, InterruptedException {

        //MQ发送消息
        rocketMQProducer.sendMsg(myTopic, JSONObject.parseObject(msg));

        //Kafka发送消息
        kafkaProducer.sendKafka(myTopic, msg);
        kafkaProducer.sendKafka(myTopic2,JSONObject.toJSONString(msg));

    }
}
