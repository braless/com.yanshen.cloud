package com.yanshen.dev.rocketmq;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;

import com.yanshen.dev.tutils.CommonUtils;
import com.yanshen.dev.tutils.MD5Util;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author luojiancheng
 * @description
 * @date 2022/06/05
 */
@Component
public class RocketProducer {
    private static final Logger logger = LoggerFactory.getLogger(RocketProducer.class);

    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    DefaultMQProducer defaultMQProducer;

    @Value("${rocketmq.producer.topic}")
    private String topic;
    @Value("${rocketmq.md5key}")
    private String md5key;

    /**
     * 同步发送
     *
     * @param msgType 消息类型
     * @param data    请求报文
     */
    public void sendMsg(String msgType, JSONObject data) {
        RocketMsgVo vo = new RocketMsgVo();
        vo.setTopic(topic);
        vo.setTag(msgType);
        vo.setCommand(msgType);
        vo.setParam(CommonUtils.getAscString(data));
        String signParam = vo.getCommand() + vo.getParam() + vo.getTimestamp() + md5key;
        String sign = MD5Util.stringToMD5(signParam);
        vo.setSign(sign);
        logger.info("RocketMQ_send发送消息:{}", JSONObject.toJSONString(vo));
        rocketMQTemplate.send(vo.getTopic() + ":" + vo.getTag(),
                MessageBuilder.withPayload(JSONObject.toJSON(vo)).build());
    }

    /**
     * 异步发送
     *
     * @param msgType 消息类型
     * @param data    请求报文
     */
    public void asyncSendMsg(String msgType, JSONObject data) {
        RocketMsgVo vo = new RocketMsgVo();
        vo.setTopic(topic);
        vo.setTag(msgType);
        vo.setCommand(msgType);
        vo.setParam(CommonUtils.getAscString(data));
        String signParam = vo.getCommand() + vo.getParam() + vo.getTimestamp() + md5key;
        String sign = MD5Util.stringToMD5(signParam);
        vo.setSign(sign);
        logger.info("syncSend发送消息:{}", JSONObject.toJSONString(vo));

        rocketMQTemplate.asyncSend(vo.getTopic() + ":" + vo.getTag(), JSONObject.toJSON(vo), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                logger.info("消息发送成功:{}", sendResult.getSendStatus());
            }

            @Override
            public void onException(Throwable throwable) {
                logger.info("mqMsg={}消息发送失败,原因:{}", JSONObject.toJSONString(vo), throwable.toString());
            }
        });

        //SendResult sendResult = rocketMQTemplate.syncSend(vo.getTopic(), JSONObject.toJSON(vo), 1000);
    }


    /**
     * 顺序发送消息
     *
     * @param msgType 消息类型
     * @param data    请求报文
     */
    public void sendMsgOrderly(String topic, String msgType, JSONObject data) {
        // 源码导读：
        // * @param hashKey    use this key to select queue. for example: orderId, productId ...
        // 翻译过来为： 使用这个hashKey去选择投递到哪个队列，比如可以设置为:orderId 或则 productId
        // FAQ：rocketMQ创建topic的时候默认的队列长度为16个，那么这个hashKey，怎么通过自己设置的orderId或则producId变成队列标示的比如如果16和队列这个值应该在1-16之间？
        // 继续看源码：
        // List<MessageQueue> messageQueueList = mQClientFactory.getMQAdminImpl().parsePublishMessageQueues(topicPublishInfo.getMessageQueueList());
        // 上面这个方法就能获取这个topic创建的时候的队列长度。然后根据它底层有个取模的方法 取到其中一个队列
        // 取模的算法很简单 ：org.apache.rocketmq.client.producer.selector.SelectMessageQueueByHash.select
        // 所以为什么这个方法的源码说我们的hashkey可以设置为：orderId和productId,这样同样的orderId就可以进入同一个队列 按照顺序消费
        //EX:顺序消费的底层源码实现就是必须选择一个队列，然后在这个对面里面的消息和生产顺序保持一致。

        RocketMsgVo vo = new RocketMsgVo();
        String orderId = "123456";
        vo.setTag(msgType);
        vo.setCommand(msgType);
        vo.setParam(CommonUtils.getAscString(data));
        String signParam = vo.getCommand() + vo.getParam() + vo.getTimestamp() + md5key;
        String sign = MD5Util.stringToMD5(signParam);
        vo.setSign(sign);
        logger.info("syncSend发送消息:{}", data.get("name"));
        SendResult sendResult = rocketMQTemplate.syncSendOrderly(topic, JSONObject.toJSON(vo), orderId);
        logger.info("同步发送顺序消息返回结果:{}", sendResult.getSendStatus());
    }


    /**
     * 顺序发送消息
     *
     * @param msgType 消息类型
     * @param data    请求报文
     */
    public void sendMsgOrderly_(String msgType, JSONObject data) throws MQBrokerException, RemotingException, InterruptedException, MQClientException {

        data.put("key", "007");
        Message message = new Message("sync-topic-0", "2", "2", data.toString().getBytes());
        Integer orderId = Integer.valueOf(data.getString("name"));
/**
 * lambda写法
 */
        SendResult sendResultRMQ = defaultMQProducer.send(message, (mqs, msg, arg) -> {
            Integer id = (Integer) arg;
            int index = id % mqs.size();
            return mqs.get(index);
        }, orderId, 300000);

/**
 * 原始写法
 */
//        SendResult sendResultRMQ = defaultMQProducer.send(message, new MessageQueueSelector() {
//            @Override
//            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
//                Integer id = (Integer) arg;
//                int index = id % mqs.size();
//                return mqs.get(index);
//            }
//        }, 123456,300000);


//        SendResult sendResultRMQ =  defaultMQProducer.send(
//                    // 要发的那条消息
//                    message,
//                    // queue 选择器 ，向 topic中的哪个queue去写消息
//                    new MessageQueueSelector() {
//                        // 手动 选择一个queue
//                        @Override
//                        public MessageQueue select(
//                                // 当前topic 里面包含的所有queue
//                                List<MessageQueue> mqs,
//                                // 具体要发的那条消息
//                                Message msg,
//                                // 对应到 send（） 里的 args，也就是2000前面的那个0
//                                Object arg) {
//                            // 向固定的一个queue里写消息，比如这里就是向第一个queue里写消息
//                            if (Integer.parseInt(arg.toString()) % 2 == 0) {
//                                return mqs.get(0);
//                            } else {
//                                return mqs.get(1);
//                            }
//                        }
//                    },
//                    // 自定义参数：0
//                    // 2000代表2000毫秒超时时间
//                orderId, 2000);

        logger.info("反馈:{}", sendResultRMQ.getSendStatus());
//
    }

    //发送事务消息
    public TransactionSendResult sendMessageInTransaction(String data, String topic,String tag) {
        return rocketMQTemplate.sendMessageInTransaction(topic+ (ObjectUtil.isEmpty(tag)?null:":"+tag), MessageBuilder.withPayload(data).build(), null);
    }

    /**
     * RocketMQ的消息发送方式主要含syncSend()同步发送、asyncSend()异步发送、sendOneWay()三种方式，sendOneWay()也是异步发送，
     * 区别在于不需等待Broker返回确认，所以可能会存在信息丢失的状况，但吞吐量更高，具体需根据业务情况选用。
     * 性能：sendOneWay > asyncSend > syncSend RocketMQTemplate的send()方法默认是同步(syncSend)的,更多可看源码实现。
     *
     * @param data
     * @param topic
     */
    public void sendOneway(String topic, Object data) {
        rocketMQTemplate.sendOneWay(topic,data);
    }


    //发送普通同步消息-Object
    void syncSend(String destination, Object payload) {

    }

    //发送普通同步消息-Message
    void syncSend(String destination, Message message) {

    }

//    //发送批量普通同步消息
//   void syncSend(String destination, Collection<T> messages){
//
//   }


    //发送普通同步消息-Object，并设置发送超时时间
    void syncSend(String destination, Object payload, long timeout) {

    }

    //发送普通同步消息-Message，并设置发送超时时间
    void syncSend(String destination, Message message, long timeout) {

    }

//    //发送批量普通同步消息，并设置发送超时时间
//    void  syncSend(String destination, Collection<T> messages, long timeout){
//
//    }


    //发送普通同步延迟消息，并设置超时，这个下文会演示
    void syncSend(String destination, Message message, long timeout, int delayLevel) {

    }

}
