package com.yanshen.dev.rocketmq.transcationMQ;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.yanshen.dev.tutils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Auther: cyc
 * @Date: 2023/3/22 16:17
 * @Description:
 */
@Slf4j
//@Component
@RocketMQMessageListener(consumeMode = ConsumeMode.ORDERLY,topic = "order", consumerGroup = "group-order", selectorExpression = "*")
public class BusinessConsumer implements RocketMQListener<MessageExt> {

    @Autowired
    RedisUtil redisUtil;
    @Override
    public void onMessage(MessageExt messageExt) {
        String fullName=this.getClass().getName();
        String[] clzNames=fullName.split("\\.");
        String clzName=clzNames[clzNames.length-1];
        String body =new String(messageExt.getBody());
        JSONObject object = JSON.parseObject(body);
        log.info(clzName+"收到消息:{},事务id: {}", body,messageExt.getTransactionId());

        //TODO
        redisUtil.set("transcation_"+messageExt.getMsgId(),"SUCCESS");
    }
}
