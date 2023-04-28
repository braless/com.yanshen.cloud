package com.yanshen.dev.kafka.main_test;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Arrays;
import java.util.Properties;

/**
 * @Auther: cyc
 * @Date: 2023/3/3 18:07
 * @Description:
 */
public class KafkaConsumer {

    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.0.47:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG ,"kafeidou_group") ;
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put("auto.offset.reset", "earliest");

        Consumer<String, String> consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("kafeidou"));

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(1000);
            for (ConsumerRecord<String, String> record : records) {
                if (null!=record.key() && record.key().startsWith("192")){
                    System.out.printf("消费者收到消息: offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
                }
            }
            Thread.sleep(3000);
        }
    }
}