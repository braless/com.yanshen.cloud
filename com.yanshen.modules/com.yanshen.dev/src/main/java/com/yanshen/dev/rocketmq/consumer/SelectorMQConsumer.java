package com.yanshen.dev.rocketmq.consumer;


import com.yanshen.dev.rocketmq.entity.OrderPaidEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * @Author: zhouliang
 * @Date: 2019/7/12 11:06
 */
@Slf4j
@Service
@RocketMQMessageListener(consumeMode = ConsumeMode.CONCURRENTLY, selectorType = SelectorType.TAG, topic = "selector_topic",
        consumerGroup = "my-consumer_selector_topic", selectorExpression = "tag1")
public class SelectorMQConsumer implements RocketMQListener<OrderPaidEvent> {

    @Override
    public void onMessage(OrderPaidEvent message) {
        log.info("tag1======>>消息过滤消费端接收到消息:{}", message);
    }
}
