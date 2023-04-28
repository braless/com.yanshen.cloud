package com.yanshen.dev.rocketmq.transcationMQ;


import com.alibaba.fastjson.JSONObject;
import com.yanshen.dev.rocketmq.transcationMQ.bean.OrderDTO;
import com.yanshen.dev.tutils.RedisUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Author: zhouliang
 * @Date: 2019/7/11 20:13
 *  注意：从 RocketMQ-Spring 2.1.0版本之后，注解@RocketMQTransactionListener不能设置 txProducerGroup、ak、sk，这些值均与对应的RocketMQTemplate保持一致。
 */
@Slf4j
//@RocketMQTransactionListener(txProducerGroup = "test-txProducerGroup-name")
@RocketMQTransactionListener()
public class TransactionListenerImpl implements RocketMQLocalTransactionListener {


    @Autowired
    TransactionLogService transactionLogService;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    OrderServiceImpl orderService;
    /**
     * RocketMQLocalTransactionState说明：
     *
     * COMMIT：
     *         表示事务消息被提交，会被正确分发给消费者。(事务消息先会发送到broker，对消费端不可见，为UNKNOWN状态，在这里回调的时候如果返回COMMIT
     *         那么。消费端马上就会接收到消息。)
     *
     * ROLLBACK：
     *          该状态表示该事务消息被回滚，因为本地事务逻辑执行失败导致（如数据库异常，或SQL异常，或其他未知异常，这时候消息在broker会被删除掉，
     *          不会发送到consumer）
     * UNKNOWN：
     *        表示事务消息未确定，可能是业务方执行本地事务逻辑时间耗时过长或者网络原因等引起的，该状态会导致broker对事务消息进行回查，默认回查
     *        总次数是15次，第一次回查间隔时间是6s，后续每次间隔60s,（消息一旦发送状态就为中间状态：UNKNOWN）
     */


    /**
     * 执行本地的事务逻辑
     *
     * @param msg
     * @param arg
     * @return
     */
    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        log.info("执行本地事务逻辑：{},arg:{}", msg,arg);
        CountDownLatch latch = null;
        try {
            latch = (CountDownLatch) arg;
            byte[] body = (byte[]) msg.getPayload();
            String json = new String(body, RemotingHelper.DEFAULT_CHARSET);
            JSONObject object = JSONObject.parseObject(json);
            //执行本地事务，比如下单成功，存储订单信息。
            assert object != null;
            log.info("执行本地事务接收到到的消息内容为:{}", object);
            //模拟现实中的业务逻辑
            TimeUnit.SECONDS.sleep(2);
            //本地事务执行成功。
            String transcationId=(String) msg.getHeaders().get("rocketmq_TRANSACTION_ID");
            //存储本地事务id
            OrderDTO order = JSONObject.parseObject(body, OrderDTO.class);
            orderService.createOrder(order,transcationId);
            redisUtil.set("transcation_"+transcationId,object.toString(),120);
            return RocketMQLocalTransactionState.COMMIT;//此处unknown 是为了下面的能回查  开发情况下应该为commit
        } catch (Exception e) {
            e.printStackTrace();
            log.error("本地事务执行失败：如数据库异常，或SQL异常，或其他未知异常，异常原因:{}", e.getMessage());
            return RocketMQLocalTransactionState.ROLLBACK;
        } finally {
            if (null != latch) {
                //事务提交完成，或代码执行完成一定要把CountDownLatch 归0，不然会造成主线程阻塞。
                latch.countDown();
            }
        }
    }

    /**
     * 执行事务回查逻辑
     *
     * @param msg
     * @return
     */
    @SneakyThrows
    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message msg) {
        byte[] body = (byte[]) msg.getPayload();
        String json = new String(body, RemotingHelper.DEFAULT_CHARSET);
        MessageHeaders headers = msg.getHeaders();
        RocketMQLocalTransactionState state;
        String transcationId=(String) headers.get("rocketmq_TRANSACTION_ID");
        String topic =(String) headers.get("rocketmq_TOPIC");
        //log.info("执行本地事务回查:{},headers:{}",json,headers);
        log.info("执行本地事务回查事务id:{},topic: {}",transcationId,topic);
        if (transactionLogService.get(transcationId)>0){
            state = RocketMQLocalTransactionState.COMMIT;

        }else {
            state = RocketMQLocalTransactionState.UNKNOWN;
        }
        //6s 本地事务还没执行完成，就会触发回调检查的方法。

        //这时候就要做自己对这个订单的异常处理，最后根据处理的情况来决定，
        // 重新发送这个订单到消费者 还是删除还是继续回查。

//     如：   return RocketMQLocalTransactionState.COMMIT; 重新发送到消费端
//        return RocketMQLocalTransactionState.ROLLBACK;  消费在broker删除，不会发送到消费端

        log.info("结束本地事务状态查询：{}",state);
        return state;
    }
}
