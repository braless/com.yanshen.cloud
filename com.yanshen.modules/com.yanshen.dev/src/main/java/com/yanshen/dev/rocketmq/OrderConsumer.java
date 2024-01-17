package com.yanshen.dev.rocketmq;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Component;

/**
 * @Auther: cyc
 * @Date: 2023/3/16 13:13
 * @Description:
 */
@Slf4j
@Component
@RocketMQMessageListener(consumeMode = ConsumeMode.ORDERLY,topic = "sync-topic-order", consumerGroup = "sync-group-order", selectorExpression = "*")
public class OrderConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {


    @Override
    public void onMessage(MessageExt messageExt) {
        byte[] body = messageExt.getBody();
        String s = new String(body);
        log.info("OrderConsumer_MQ顺序消费MQ收到消息:{}",JSONObject.parse(s));
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        // 每次拉取的间隔，单位为毫秒
        consumer.setPullInterval(2000);
        // 设置每次从队列中拉取的消息数为16
        consumer.setPullBatchSize(16);
    }

}
