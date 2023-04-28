package com.yanshen.dev.rocketmq.transcationMQ;

import cn.hutool.core.lang.Snowflake;
import com.alibaba.fastjson.JSON;
import com.yanshen.dev.mapper.TranscationDAO;
import com.yanshen.dev.rocketmq.RocketMsgVo;
import com.yanshen.dev.rocketmq.RocketProducer;
import com.yanshen.dev.rocketmq.RocketProducer1;
import com.yanshen.dev.rocketmq.transcationMQ.bean.OrderDTO;
import com.yanshen.dev.rocketmq.transcationMQ.bean.TransactionLog;
import com.yanshen.dev.tutils.RedisUtil;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @Auther: cyc
 * @Date: 2023/3/22 14:27
 * @Description:
 */
@Service
public class OrderServiceImpl implements OrderService {

    //    @Autowired
//    OrderMapper orderMapper;
//    @Autowired
//    TransactionLogMapper transactionLogMapper;
    @Autowired
    RocketProducer producer;
    //TransactionProducer producer;

    @Autowired
    RocketProducer1 producer1;


    @Autowired
    RedisUtil redisUtil;

    @Autowired
    TranscationDAO transcationDAO;



    Snowflake snowflake = new Snowflake(1, 1);
    Logger logger = LoggerFactory.getLogger(this.getClass());

    //执行本地事务时调用，将订单数据和事务日志写入本地数据库
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createOrder(OrderDTO orderDTO, String transactionId) {

//        //1.创建订单
//        Order order = new Order();
//        BeanUtils.copyProperties(orderDTO,order);
//        orderMapper.createOrder(order);

        //写入事务日志
        TransactionLog log = new TransactionLog();
        log.setTranscationId(transactionId);
        log.setId(transactionId);
        log.setBusiness("order");
        log.setMsg("order");
        log.setForeignKey(String.valueOf(orderDTO.getId()));
        transcationDAO.insertLog(log);
        //int i=1/0;
        redisUtil.set("transcation_" + transactionId, log.toString(), 120);
        logger.info("订单创建完成。{}", orderDTO);
    }

    //前端调用，只用于向RocketMQ发送事务消息
    @Override
    public TransactionSendResult createOrder(OrderDTO order) throws MQClientException {
        String topic="order";
        order.setId(snowflake.nextId());
        order.setOrderNo(snowflake.nextIdStr());
        RocketMsgVo vo =new RocketMsgVo();
        vo.setTag("TAG-ORDER");
        vo.setTopic(topic);
        vo.setParam(order);
        //说明:发送消息的内容基本一致，唯一的区别在于采用的是不同的模板。
        LocalDateTime.now();
        TransactionSendResult result = producer.sendMessageInTransaction(JSON.toJSONString(vo),topic,vo.getTag() );
        //producer1.sendMessageInTransaction("order-1",JSON.toJSONString(order));
        return result;
    }

    @Transactional
    public void insert() {
        try {
            transcationDAO.update();
            int i = 1 / 0;
            logger.info("updateSuccess!");
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }
}
