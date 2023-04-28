package com.yanshen.dev.kafka;


import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import java.util.concurrent.ExecutionException;

/**
 * kafka发送简单封装
 * @author guoxingyong
 * @data 2019/4/25 14:14
 */
@Component
public class KafkaProducer {
	private final static Logger LOG = LoggerFactory.getLogger(KafkaProducer.class);

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	public void sendKafka(String topic, String msg) throws InterruptedException, ExecutionException {
		this.sendKafka(topic, null, msg, (Throwable throwable) -> defaultOnFail(throwable, topic, msg), (SendResult sendResult) -> defaultOnSuccess(topic, msg));
	}

	public void sendKafka(String topic, String key, String msg) throws InterruptedException, ExecutionException {
		this.sendKafka(topic, key, msg, (Throwable throwable) -> defaultOnFail(throwable, topic, msg), (SendResult sendResult) -> defaultOnSuccess(topic, msg));
	}


	//发送kafka 并且传入发送的信息  (异常情况,正常情况)
	public void sendKafka(String topic, String key, String msg, FailureCallback failureCallback, SuccessCallback<? super SendResult> successCallback) throws InterruptedException, ExecutionException {
		ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, key, msg);
		//future.addCallback(successCallback, failureCallback);
		//future.get();
		future.addCallback(result -> LOG.info("生产者成功发送消息到topic:`{}` partition: {} 的消息", result.getRecordMetadata().topic(), result.getRecordMetadata().partition(),result.getProducerRecord()),
				ex -> LOG.error("生产者发送消失败，原因：{}", ex.getMessage()));
	}


	public void sendKafka(String topic, String msg, FailureCallback failureCallback, SuccessCallback<? super SendResult> successCallback) throws InterruptedException, ExecutionException {
		this.sendKafka(topic,null,msg,failureCallback,successCallback);
	}


	public static void defaultOnFail(Throwable throwable, String topic, String msg) {
		LOG.info("send kafka fail,topic:{},message:{}", topic, msg);
		LOG.error("fail exception", throwable);
	}

	public static void defaultOnSuccess(String topic, String msg) {
		//LOG.info("send kafka success,topic:{},message:{}", topic, msg);
	}
//	public void sendKafka(String topic, Object o) {
//		ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, o);
//		future.addCallback(
//				result -> LOG.info("生产者成功发送消息到topic:{} partition:{}的消息", result.getRecordMetadata().topic(), result.getRecordMetadata().partition()),
//				ex -> LOG.error("生产者发送消失败，原因：{}", ex.getMessage()));
//	}
	//如果我们想在发送的时候带上timestamp（时间戳）、key等信息的话，sendMessage()方法可以这样写：

	public void sendMessage(String topic, Object o) {
		// 分区编号最好为 null，交给 kafka 自己去分配
		ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(topic, null, System.currentTimeMillis(), String.valueOf(o.hashCode()), o);
		ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(producerRecord);
		future.addCallback(result -> LOG.info("生产者成功发送消息到topic:{} partition:{}的消息", result.getRecordMetadata().topic(), result.getRecordMetadata().partition()),
				ex -> LOG.error("生产者发送消失败，原因：{}", ex.getMessage()));
	}
}
