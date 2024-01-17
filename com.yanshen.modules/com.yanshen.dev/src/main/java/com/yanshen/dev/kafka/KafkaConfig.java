package com.yanshen.dev.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;

import javax.annotation.Resource;

/**
 * @Auther: cyc
 * @Date: 2023/3/14 17:34
 * @Description:
 */
@Configuration
public class KafkaConfig {
    @Value("${kafka.topic.kafeidou_1}")
    String myTopic;
    @Value("${kafka.topic.kafeidou_2}")
    String myTopic2;

//    /**
//     * JSON消息转换器
//     */
//    @Bean
//    public RecordMessageConverter jsonConverter() {
//        return new StringJsonMessageConverter();
//    }

    /**
     * 通过注入一个 NewTopic 类型的 Bean 来创建 topic，如果 topic 已存在，则会忽略。
     */
    @Bean
    public NewTopic myTopic() {
        return new NewTopic(myTopic, 2, (short) 1);
    }

    @Bean
    public NewTopic myTopic2() {
        return new NewTopic(myTopic2, 1, (short) 1);
    }

    @Resource
    private ConsumerFactory<Integer,String> consumerFactory;

    /**
     * 开启批量消费
     * @return 注入bean
     */
    @Bean("batchMessageFactory")
    public KafkaListenerContainerFactory<?> batchFactory() {
        ConcurrentKafkaListenerContainerFactory<Integer, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setConcurrency(4);
        //设置为批量消费，每个批次数量在Kafka配置参数中设置ConsumerConfig.MAX_POLL_RECORDS_CONFIG
        factory.setBatchListener(true);
        //设置提交偏移量的方式
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        return factory;
    }
}
