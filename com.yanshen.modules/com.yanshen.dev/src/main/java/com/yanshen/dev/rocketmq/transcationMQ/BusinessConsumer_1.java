package com.yanshen.dev.rocketmq.transcationMQ;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Auther: cyc
 * @Date: 2023/3/22 18:49
 * @Description:
 */
@Slf4j
@Component
@RocketMQMessageListener(consumeMode = ConsumeMode.ORDERLY,topic = "order-1", consumerGroup = "group-order-1", selectorExpression = "*")
public class BusinessConsumer_1 implements RocketMQListener<JSONObject> {
    @Override
    public void onMessage(JSONObject jsonObject) {
        String fullName=this.getClass().getName();
        String[] clzNames=fullName.split("\\.");
        String clzName=clzNames[clzNames.length-1];
        log.info(clzName+"收到消息:{}", jsonObject.toString());
    }
}
