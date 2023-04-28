package com.yanshen.dev.rocketmq.transcationMQ;

import cn.hutool.core.date.DateTime;
import com.yanshen.dev.base.ApiResult;
import com.yanshen.dev.rocketmq.transcationMQ.bean.OrderDTO;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: cyc
 * @Date: 2023/3/22 14:36
 * @Description:
 */
@RestController
public class TransactionController {

    @Autowired
    OrderServiceImpl orderService;
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostMapping("/create_order")
    public ApiResult createOrder(@RequestBody OrderDTO order) throws MQClientException {
        logger.info("接收到订单数据：{}",order.getCommodityCode());
        order.setTimeStr(DateTime.now().toTimeStr());
        TransactionSendResult order1 = orderService.createOrder(order);
        //orderService.insert();
        return ApiResult.success(order1);
    }
}