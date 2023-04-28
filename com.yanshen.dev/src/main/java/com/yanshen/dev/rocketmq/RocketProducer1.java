package com.yanshen.dev.rocketmq;

import com.yanshen.dev.rocketmq.transcationMQ.ExtRocketMQTemplate;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

/**
 * @Auther: cyc
 * @Date: 2023/3/22 18:40
 * @Description:
 */
@Component
public class RocketProducer1 {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ExtRocketMQTemplate extRocketMQTemplate;

    public TransactionSendResult sendMessageInTransaction(String topic, String msg) {
        logger.info("2号模板发送事务消息:{}", msg);
        Message message = new Message();
        message.setTopic(topic);
        message.setBody(msg.getBytes());
        message.setTags("TAGA");
        TransactionSendResult result = extRocketMQTemplate.sendMessageInTransaction(topic,
                MessageBuilder.withPayload(msg).build(), msg);

        //发送状态
        String sendStatus = result.getSendStatus().name();
        // 本地事务执行状态
        String localTxState = result.getLocalTransactionState().name();
        logger.info("2号模板发送事务消息 sendStatus:{},localTXState:{}", sendStatus, localTxState);
        return  result;
    }
}
