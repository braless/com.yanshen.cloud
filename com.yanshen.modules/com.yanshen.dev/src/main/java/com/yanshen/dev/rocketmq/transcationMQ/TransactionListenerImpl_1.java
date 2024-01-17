package com.yanshen.dev.rocketmq.transcationMQ;

import com.yanshen.dev.tutils.RedisUtil;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;

import java.nio.charset.StandardCharsets;

/**
 * @Auther: cyc
 * @Date: 2023/3/22 18:37
 * @Description:  https://juejin.cn/post/7133245434201374734    Spring Boot集成RocketMq如何发送多个事务消息?
 */

//扩展监听器
@RocketMQTransactionListener(rocketMQTemplateBeanName = "extRocketMQTemplate")
public class TransactionListenerImpl_1 implements RocketMQLocalTransactionListener {


    @Autowired
    TransactionLogService transactionLogService;

    @Autowired
    RedisUtil redisUtil;


    private Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 执行本地事务
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg,
                                                                 Object obj) {
        logger.info("2号本地事务开始执行---{},obj: {}",msg,obj);
        RocketMQLocalTransactionState resultState = RocketMQLocalTransactionState.COMMIT;
        try {
            //处理业务
            String jsonStr = new String((byte[]) msg.getPayload(), StandardCharsets.UTF_8);
            //本地事务执行成功。
            String transcationId=(String) msg.getHeaders().get("rocketmq_TRANSACTION_ID");
            //存储本地事务id
            redisUtil.set("transcation_"+transcationId,jsonStr,120);
            logger.info("2号执行本地事务接收到到的消息内容为:{}", obj);
        } catch (Exception e) {
            logger.error("2号本地事务开始执行---异常", e);
            resultState = RocketMQLocalTransactionState.UNKNOWN;
        }

        return resultState;
    }

    /**
     * 检查本地事务的状态
     */
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        RocketMQLocalTransactionState resultState;
        try {
            String jsonStr = new String((byte[]) msg.getPayload(), StandardCharsets.UTF_8);
            String transcationId=(String) msg.getHeaders().get("rocketmq_TRANSACTION_ID");
            //logger.info("2号执行本地事务回查:{},headers:{}",jsonStr,msg.getHeaders());
            logger.info("2号执行本地事务回查事务id:{}",transcationId);
            if (transactionLogService.get(transcationId)>0){
                resultState = RocketMQLocalTransactionState.COMMIT;

            }else {
                resultState = RocketMQLocalTransactionState.UNKNOWN;
            }
            //logger.info("2号本地事务开始回查事务结果:{}", jsonStr);
        } catch (Exception e) {
            resultState = RocketMQLocalTransactionState.ROLLBACK;
        }
        logger.info("2号结束本地事务状态查询：{}",resultState);
        return resultState;
    }
}
