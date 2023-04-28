package com.yanshen.dev.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yanshen.dev.module.WeiboModel;
import com.yanshen.dev.service.TcpService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Auther: cyc
 * @Date: 2023/3/7 19:07
 * @Description:
 */
@Service
public class KafeidouConsumer {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public static long startTime=Long.MAX_VALUE;
    public static long endTime=0;
    public static ThreadLocal<Long> time = new ThreadLocal<>();
    public static ThreadLocal<String> groupNo = new ThreadLocal<>();
    public static String getGroupNo() {
        return groupNo.get();
    }


    @Autowired
    TcpService tcpService;


    /**
     * 	监听Kafka报警协议数据
     * @param records
     */
    @KafkaListener(topics = { "kafeidou_1" })
    public void kafeidou_1(ConsumerRecord<?, ?> records) {
        try{
            //logger.info("Kafka開始消費:{}",records.topic());
            SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timestamp = dateFormat.format(new Date(records.timestamp()));
            logger.info("Kafka開始消費: {},offset: {},消费的值 :{},partition:{}" ,records.topic(),records.offset(),timestamp,records.partition());
            WeiboModel model =JSON.parseObject((String) records.value(),WeiboModel.class);
            System.out.println(model);

        }catch(Exception e){
            logger.error("", e);
        }
    }
    /**
     * 	监听Kafka报警协议数据
     * @param records
     */
    @KafkaListener(topics = { "kafeidou_2" },concurrency = "4",containerFactory = "batchMessageFactory")
    public void kafeidou_2(List<ConsumerRecord<String, String>> records, Acknowledgment ack) {
        try{
            logger.info("Kafka開始消費:{}",records.get(0).topic());
            try {
                records.forEach(it -> {
                   // kafeidou_1(it);
                    SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String timestamp = dateFormat.format(new Date(it.timestamp()));
                    logger.info("消费的topic: {},offset: {},消费的值 :{}" ,it.topic(),it.offset(),timestamp);
                });
            } finally {
                ack.acknowledge();  //手动提交偏移量
            }
        }catch(Exception e){
            logger.error("", e);
        }
    }

    /**
     * 	监听Kafka报警协议数据
     * @param records
     */
    @KafkaListener(topics = { "test" })
    public void listen(List<ConsumerRecord<?, ?>> records) {
        try{
            String[] ss = UUID.randomUUID().toString().split("-");
            groupNo.set(ss[ss.length - 1]);

            long a = System.currentTimeMillis();
            setStartTime(a);
            time.set(a);
            records.forEach(rc->{
                JSONObject jsonObject = JSON.parseObject(rc.value().toString());
                logger.info("解析消息:",jsonObject);
            });
            //tcpService.listenKafkaAlarm(records);
            logger.info("Test批次："+groupNo.get()+",执行完毕,消费数量--------------------"+records.size(),"消息内容{}",records);
            long end = System.currentTimeMillis();
            long distance = end - a;
            logger.info("Test批次："+groupNo.get()+",执行完毕，处理时间--------------------"+distance);
            setEndTime(end);
        }catch(Exception e){
            logger.error("", e);
        }
    }





    public static void setStartTime(long a) {
        if(a < startTime) {
            startTime = a;
        }
    }
    public static void setEndTime(long a) {
        if(endTime < a) {
            endTime = a;
        }
    }

}
