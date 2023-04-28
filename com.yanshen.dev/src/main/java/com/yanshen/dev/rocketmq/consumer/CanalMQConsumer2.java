package com.yanshen.dev.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;


/**
 * @Author: zhouliang
 * @Date: 2019/7/11 14:52
 */
@Slf4j
@Service
@RocketMQMessageListener(consumeMode = ConsumeMode.ORDERLY, topic = "united_order_data_sync_topic", consumerGroup = "my_test_group2")
public class CanalMQConsumer2 implements RocketMQListener<MessageExt> {

    @Override
    public void onMessage(MessageExt message) {
        try {
            String msg = new String(message.getBody(), RemotingHelper.DEFAULT_CHARSET);
            log.info("canal united ===>>>> {}", msg);
        } catch (Exception e) {
            log.error("", e);
        }
    }
}
