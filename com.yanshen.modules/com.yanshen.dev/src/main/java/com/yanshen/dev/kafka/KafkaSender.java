package com.yanshen.dev.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class KafkaSender {

	@Autowired
    KafkaProducer kafkaProducer;

	public void doSend(List<String> l, String toppic) throws InterruptedException, ExecutionException {
		for (String s : l) {
			kafkaProducer.sendKafka(toppic, s);
		}
	}
}