package com.yanshen.dev.listener;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry.*;
import com.alibaba.otter.canal.protocol.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.yanshen.dev.constant.KafKaTopicConst;
//import com.yanshen.boot.kafka.KafkaProducer;
//import com.yanshen.boot.kafka.KafkaSender;
import com.yanshen.dev.kafka.KafkaProducer;
import com.yanshen.dev.kafka.KafkaSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;


@Service
@SuppressWarnings("resource")
public class CanalListener implements DisposableBean {
    private static final Logger logger = LoggerFactory.getLogger(CanalListener.class);
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Gson gson = new Gson();
    private static boolean exit = false;
    private static final Map<String, List<String>> tableDatas = new HashMap<>();

    @Override
    public void destroy() throws Exception {
        logger.info("CanalListener destroy start ....");
        exit = true;
        logger.info("CanalListener destroy end ....");
    }

    @Autowired
    KafkaSender kafkaSender;
	@Autowired
    KafkaProducer kafkaProducer;
    @Value("${canal.ip}")
    String canalServer;
    @Value("/file")
    String logfilePath;
    @Value("10")
    Integer onesize;
    @Value(".")
    String schema;

    public void doIt() {
        /////////////////////////////////规则配置///////////////////////////////////////////
        Map<String, RowChangeRule> rules = new HashMap<>();

        RowChangeRule htsaRule = new RowChangeRule();
        htsaRule.setColumnZzid("socreaccountzzid");
        htsaRule.setColumnUuid("scoreaccountid");
        htsaRule.setColumnTime("createtime");
        rules.put("h_tenant_socre_account", htsaRule);


        /////////////////////////////////规则配置 End///////////////////////////////////

        FileChannel fileChannel = getFileChannel();
        ByteBuffer buf = getByteBuffer();

        int batchSize = 10000;
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(canalServer, 11111), "example", "", "");
        connector.connect();
        //connector.subscribe(schema + String.join("," + schema, rules.keySet()));
        connector.subscribe(".*\\..*");
        int i = 0;
        int j = 0;
        int rollbackFlag = 0;
        while (true) {
            if (exit == true) break;

            if (rollbackFlag == 1) {
                connector = CanalConnectors.newSingleConnector(new InetSocketAddress(canalServer, 11111), "example", "", "");
                connector.connect();
                //connector.subscribe(schema + String.join("," + schema, rules.keySet()));
                connector.subscribe(".*\\..*");
                rollbackFlag = 0;
            }

            long batchId = 0;
            Message message = null;
            try {
                if (i++ > 1000) {
                    logger.info("Canal connector 开始检查可用性");
                    boolean valid = connector.checkValid();
                    logger.info("Canal connector 可用性：" + valid);
                    if (valid == false) {
                        connector.disconnect();
                        connector.connect();
                    } else {
                        i = 0;
                    }
                }
                if (j++ > 10000) {
                    fileChannel = getFileChannel();
                    buf = getByteBuffer();
                }

                message = connector.getWithoutAck(batchSize, 10L, TimeUnit.SECONDS);
                if (message == null) {
                    connector.disconnect();
                    connector.connect();
                    continue;
                }
                batchId = message.getId();

                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                } else {
                    long a = System.currentTimeMillis();
                    //printEntry(message.getEntries(), batchId, rules, fileChannel, buf);
                    printEntry(message.getEntries());
                    logger.info(ThreadLocalUtils.getStringBuilder().append("批次：").append(batchId).append("，总耗时：").append(System.currentTimeMillis() - a).toString());
                }
                connector.ack(batchId);
            } catch (com.alibaba.otter.canal.protocol.exception.CanalClientException e) {
                logger.error("位置标记：1，", e);
                try {
                    connector.disconnect();
                    connector.connect();
                } catch (Exception e1) {
                    logger.error("位置标记：2，", e1);
                }
                try {
                    if (batchId > 0) connector.rollback(batchId);
                } catch (Exception e1) {
                    logger.error("位置标记：3，", e1);
                    rollbackFlag = 1;
                }
                try {
                    Thread.sleep(10000);
                } catch (Exception e1) {
                    logger.error("位置标记：4，", e1);
                }
            } catch (Exception e) {
                logger.error("位置标记：5，", e);
                try {
                    connector.disconnect();
                    connector.connect();
                } catch (Exception e1) {
                    logger.error("", e1);
                }
                try {
                    if (batchId > 0) connector.rollback(batchId);
                } catch (Exception e1) {
                    logger.error("位置标记：6，", e1);
                    rollbackFlag = 1;
                }
                try {
                    Thread.sleep(10000);
                } catch (Exception e1) {
                    logger.error("位置标记：7，", e1);
                }
            }
        }
    }
    private  void printEntry(List<Entry> entrys) throws ExecutionException, InterruptedException {
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
            System.out.println(String.format("================&gt; binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

            for (RowData rowData : rowChage.getRowDatasList()) {
                if (eventType == EventType.DELETE) {
                    printColumn(eventType.toString(),rowData.getBeforeColumnsList());
                } else if (eventType == EventType.INSERT) {
                    printColumn(eventType.toString(),rowData.getAfterColumnsList());
                } else {

                    printColumn(eventType+"修改前",rowData.getBeforeColumnsList());
                    printColumn(eventType+"修改后",rowData.getAfterColumnsList());
                }
                StringBuilder str=new StringBuilder();
                str.append("type");
                for (Column column : rowData.getBeforeColumnsList()) {
                    str.append(": "+column.getName()+"-"+column.getValue()+", update="+column.getUpdated()+" ");

                }
                kafkaProducer.sendKafka("kafeidou", str.toString());
            }
        }
    }
    private void printEntry(List<Entry> entrys, long batchId, Map<String, RowChangeRule> rules, FileChannel fileChannel, ByteBuffer buf) throws IOException, InterruptedException, ExecutionException {
        tableDatas.clear();

        long b = System.currentTimeMillis();
        for (Entry entry : entrys) {
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND || StringUtils.isEmpty(entry.getHeader().getTableName())) {
                continue;
            }

            RowChange rowChage = RowChange.parseFrom(entry.getStoreValue());
            EventType eventType = rowChage.getEventType();

            String s = String.format("[" + df.format(new Date()) + "] batchId[" + batchId + "]，binlog[%s:%s]，name[%s,%s]，eventType : %s，数据量：" + rowChage.getRowDatasList().size(), entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(), entry.getHeader().getSchemaName(), entry.getHeader().getTableName(), eventType);

            logger.info(s);

//			buf.clear();
//			buf.put((s + "\r\n").getBytes());
//			buf.flip();
//			while (buf.hasRemaining()) {
//				fileChannel.write(buf);
//			}
            logger.info(String.format("================binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));
            for (RowData rowData : rowChage.getRowDatasList()) {
                if (eventType == EventType.DELETE) {
                    printColumn("删除数据",rowData.getBeforeColumnsList());
                } else if (eventType == EventType.INSERT) {
                    printColumn("插入数据",rowData.getAfterColumnsList());
                } else {
                    printColumn("修改前的数据",rowData.getBeforeColumnsList());
                    printColumn("修改后的数据",rowData.getAfterColumnsList());
                }
            }
            kafkaProducer.sendKafka("kafeidou", "234");
            RowChangeRule rule = rules.get(entry.getHeader().getTableName());
//            if (rule == null) continue;

            List<String> jsons = tableDatas.get(entry.getHeader().getTableName());
            if (jsons == null) {
                jsons = new ArrayList<>();
                tableDatas.put(entry.getHeader().getTableName(), jsons);
            }
//            for (RowData rowData : rowChage.getRowDatasList()) {
//                RowChangeInfo rci = new RowChangeInfo();
//                Map<String, String> m;
//                if (eventType == EventType.DELETE) {
//                    m = getColumnMap(rowData.getBeforeColumnsList());
//                    rci.setOpType(DELETE);
//                    rci.setBefore(m);
//                } else if (eventType == EventType.INSERT) {
//                    m = getColumnMap(rowData.getAfterColumnsList());
//                    rci.setOpType(INSERT);
//                    rci.setAfter(m);
//                } else if (eventType == EventType.UPDATE) {
//                    m = getColumnMap(rowData.getBeforeColumnsList());
//                    rci.setOpType(UPDATE);
//                    rci.setBefore(m);
//                    rci.setAfter(getColumnMap(rowData.getAfterColumnsList()));
//                } else {
//                    continue;
//                }
//                Long zzid = !StringUtils.isEmpty(m.get(rule.getColumnZzid())) ? Long.parseLong(m.get(rule.getColumnZzid())) : 0L;
//                String uuid = m.get(rule.getColumnUuid());
//                String time = m.get(rule.getColumnTime());
//
//                rci.setTableName(entry.getHeader().getTableName());
//                rci.setZzid(zzid);
//                rci.setUuid(uuid);
//                rci.setTime(time);
//                rci.setZzidKey(rule.getColumnZzid());
//                String json = mapper.writeValueAsString(rci);
//                jsons.add(json);
//
//                buf.clear();
//                buf.put((json + "\r\n").getBytes());
//                buf.flip();
//                while (buf.hasRemaining()) {
//                    fileChannel.write(buf);
//                }
//            }
        }
        logger.info(ThreadLocalUtils.getStringBuilder().append("批次：").append(batchId).append("，数据解析总耗时：").append(System.currentTimeMillis() - b).toString());

        Iterator<Map.Entry<String, List<String>>> iter = tableDatas.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, List<String>> entry = (Map.Entry<String, List<String>>) iter.next();
            String tablename = (String) entry.getKey();
            List<String> jsons = (List<String>) entry.getValue();
            if (jsons.size() > 0) {
                long a = System.currentTimeMillis();
                if (tablename.equals("h_reserve_recycle_order") || tablename.equals("h_reserve_recycle_order_item")
                        || tablename.equals("h_every_day_one_bag") || tablename.equals("h_group_account")) {
                    kafkaProducer.sendKafka(KafKaTopicConst.OTTER_BAT_SEND_PREFIX + tablename, gson.toJson(jsons));
                } else {
                    kafkaSender.doSend(jsons, KafKaTopicConst.OTTER_SEND_PREFIX + tablename);
                }
                logger.info(ThreadLocalUtils.getStringBuilder().append("批次：").append(batchId).append("，表：").append(tablename).append("，Kafka发送耗时：").append(System.currentTimeMillis() - a).toString());
            }
        }
    }

    private Map<String, String> getColumnMap(List<Column> columns) {
        Map<String, String> m = new HashMap<>();
        for (Column column : columns) {
            m.put(column.getName(), column.getValue());
        }
        return m;
    }

    private static void printColumn(String type,List<Column> columns) {
        StringBuilder str=new StringBuilder();
        str.append(type);
        for (Column column : columns) {
            str.append(": "+column.getName()+"-"+column.getValue()+", update="+column.getUpdated()+" ");

        }
        logger.info(str.toString());
    }

    public FileChannel getFileChannel() {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(logfilePath + "." + new SimpleDateFormat("yyyyMMdd").format(new Date()), "rw");
        } catch (FileNotFoundException e) {
            logger.error("", e);
            throw new RuntimeException(e);
        }
        try {
            raf.seek(raf.length());
        } catch (IOException e) {
            logger.error("", e);
            throw new RuntimeException(e);
        } // 不覆盖源文件
        FileChannel fileChannel = raf.getChannel();
        return fileChannel;
    }

    public ByteBuffer getByteBuffer() {
        ByteBuffer buf = ByteBuffer.allocate(onesize);
        return buf;
    }
}