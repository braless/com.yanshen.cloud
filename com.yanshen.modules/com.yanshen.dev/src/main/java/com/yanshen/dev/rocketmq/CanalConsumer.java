package com.yanshen.dev.rocketmq;

import com.alibaba.fastjson.JSONObject;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author luojiancheng
 * @description
 * @date 2022/06/05
 * @Desc: RocketMQ在设计时就不希望一个消费者同时处理多个类型的消息，
 * 因此同一个consumerGroup下的consumer职责应该是一样的，
 * 不要干不同的事情（即消费多个topic）。建议consumerGroup与topic一一对应。
 *
 * https://github.com/apache/rocketmq-spring/blob/master/README_zh_CN.md FAQ:3
 */
@Component
@RocketMQMessageListener(consumeMode = ConsumeMode.CONCURRENTLY,topic = "sync-topic", consumerGroup = "sync-group", selectorExpression = "*")
public class CanalConsumer implements RocketMQListener<JSONObject> {
    private static final Logger log = LoggerFactory.getLogger(CanalConsumer.class);

    @Override
    public void onMessage(JSONObject jsonObject) {
        log.info("CanalConsumer_MQ收到消息:{}", jsonObject.toString());
        //TODO
    }
}