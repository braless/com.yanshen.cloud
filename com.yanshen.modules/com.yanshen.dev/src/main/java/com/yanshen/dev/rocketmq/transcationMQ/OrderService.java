package com.yanshen.dev.rocketmq.transcationMQ;

import com.yanshen.dev.rocketmq.transcationMQ.bean.OrderDTO;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionSendResult;

/**
 * @Auther: cyc
 * @Date: 2023/3/22 14:25
 * @Description:
 */
public interface OrderService {

     void createOrder(OrderDTO orderDTO, String transactionId);

    TransactionSendResult createOrder(OrderDTO order) throws MQClientException;
}
