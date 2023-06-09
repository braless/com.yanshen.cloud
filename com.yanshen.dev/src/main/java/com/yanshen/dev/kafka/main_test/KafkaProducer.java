package com.yanshen.dev.kafka.main_test;

import org.apache.kafka.clients.producer.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

/**
 * @Auther: cyc
 * @Date: 2023/3/3 18:05
 * @Description:
 */
public class KafkaProducer {
    public static void main(String[] args) {
        long events = 30;
        Random rnd = new Random();

        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.0.47:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("message.timeout.ms", "3000");

        Producer<String, String> producer = new org.apache.kafka.clients.producer.KafkaProducer<>(props);

        String topic = "test";

        for (long nEvents = 0; nEvents < events; nEvents++) {
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dataStr = dateFormat.format(new Date());
            long runtime = new Date().getTime();
            String ip = "192.168.0." + rnd.nextInt(255);
            String msg = dataStr + ",www.example.com," + ip;
            System.out.println(msg);
            ProducerRecord<String, String> data = new ProducerRecord<String, String>(topic, ip, msg);
            producer.send(data,
                    new Callback() {
                        public void onCompletion(RecordMetadata metadata, Exception e) {
                            if(e != null) {
                                e.printStackTrace();
                            } else {
                                System.out.println("The offset of the record we just sent is: " + metadata.offset());
                            }
                        }
                    });
        }
        System.out.println("send message done");
        producer.close();
        System.exit(-1);
    }
}