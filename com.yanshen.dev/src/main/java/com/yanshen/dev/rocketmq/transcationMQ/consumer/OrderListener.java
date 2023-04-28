package com.yanshen.dev.rocketmq.transcationMQ.consumer;

import com.alibaba.fastjson.JSONObject;
import com.yanshen.dev.rocketmq.transcationMQ.bean.OrderDTO;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Auther: cyc  原始方式 消费数据
 * @Date: 2023/3/22 15:39
 * @Description:
 */
@Component
public class OrderListener implements MessageListenerConcurrently {

//    @Autowired
//    PointsService pointsService;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext context) {
        logger.info("消费者线程监听到消息。");
        try{
            for (MessageExt message:list) {
                logger.info("开始处理订单数据，准备增加积分....");
                OrderDTO order  = JSONObject.parseObject(message.getBody(), OrderDTO.class);
                //pointsService.increasePoints(order);
                logger.info("开始消费数据:{}",order.toString());
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }catch (Exception e){
            logger.error("处理消费者数据发生异常。{}",e);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }
}