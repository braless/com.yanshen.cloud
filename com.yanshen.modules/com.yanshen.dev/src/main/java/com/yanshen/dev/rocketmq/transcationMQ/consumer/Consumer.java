package com.yanshen.dev.rocketmq.transcationMQ.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * @Auther: cyc
 * @Date: 2023/3/22 17:39
 * @Description:
 */
//@Component
public class Consumer {

    String consumerGroup = "consumer-group-order";
    DefaultMQPushConsumer consumer;

    @Autowired
    OrderListener orderListener;

    @PostConstruct
    public void init() throws MQClientException {
        consumer = new DefaultMQPushConsumer(consumerGroup);
        consumer.setNamesrvAddr("120.46.171.189:9876");
        consumer.subscribe("order","*");
        consumer.registerMessageListener(orderListener);
        consumer.start();
    }
}
