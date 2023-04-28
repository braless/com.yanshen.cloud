package com.yanshen.dev.canal;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry.*;
import com.alibaba.otter.canal.protocol.Message;
import com.yanshen.dev.kafka.KafkaProducer;
import com.yanshen.dev.kafka.MqProducer;
import com.yanshen.dev.module.WeiboModel;
import com.yanshen.dev.rocketmq.RocketProducer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @Auther: cyc
 * @Date: 2023/3/6 20:01
 * @Description:
 */
@Component
@SuppressWarnings("resource")
@Slf4j
public class CanalClients {

    @Autowired
    RocketProducer rocketMQProducer;
    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    MqProducer mqProducer;

    public void doIt() {
        // 创建链接
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("192.168.0.47", 11111), "example", "", "");
        int batchSize = 1000;
        int emptyCount = 0;
        try {
            connector.connect();
            connector.subscribe(".*\\..*");
            connector.rollback();
            int totalEmptyCount = 120;
            while (true) {
                Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    emptyCount++;
                    //System.out.println("empty count : " + emptyCount);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                } else {
                    emptyCount = 0;
                    // System.out.printf("message[batchId=%s,size=%s] \n", batchId, size);
                    printEntry(message.getEntries());
                }

                connector.ack(batchId); // 提交确认
                // connector.rollback(batchId); // 处理失败, 回滚数据
            }

            //System.out.println("empty too many times, exit");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            connector.disconnect();
        }
    }

    private void printEntry(List<Entry> entrys) throws ExecutionException, InterruptedException {
        for (Entry entry : entrys) {
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }

            RowChange rowChage = null;
            try {
                rowChage = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }

            EventType eventType = rowChage.getEventType();
            System.out.println(String.format("================ binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

            for (RowData rowData : rowChage.getRowDatasList()) {
                if (eventType == EventType.DELETE) {
                    printColumn(eventType.toString(),rowData.getBeforeColumnsList());
                } else if (eventType == EventType.INSERT) {
                    printColumn(eventType.toString(),rowData.getAfterColumnsList());
                } else {
                    System.out.println("-------数据修改前: before");
                    //printColumn(rowData.getBeforeColumnsList());
                    //System.out.println("-------数据修改后: after");
                    printColumn(eventType.toString(),rowData.getAfterColumnsList());
                }
            }
        }
    }

    private void printColumn(String type,List<Column> columns) throws ExecutionException, InterruptedException {

        StringBuilder builder=new StringBuilder();
        JSONObject object =new JSONObject();
        for (Column column : columns) {
            object.put(column.getName(),column.getValue());
            builder.append(column.getName());
            builder.append(column.getValue());

        }
        log.info("Cannal监听到{}消息:{}",type,object);
        //rocket mq
//        rocketMQProducer.sendMsg("MysqlUpdate",object);
        String topic="sync-topic-order";
        rocketMQProducer.sendMsgOrderly(topic,"CanalMQ",object);

        WeiboModel model =object.toJavaObject(WeiboModel.class);

        List<ConsumerRecord<?, ?>> records = new ArrayList<>();
       // kafkaProducer.sendKafka("kafeidou_1",object.toJSONString());
        //kafkaProducer.sendKafka("kafeidou_2",object.toJSONString());



    }

}
