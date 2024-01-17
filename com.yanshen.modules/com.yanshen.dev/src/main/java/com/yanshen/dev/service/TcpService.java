package com.yanshen.dev.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 设备接入，E0协议业务处理
 * @author zhanghuicheng
 *
 */
@Service
public interface TcpService {

	
	/**
	 * 	投放数据业务处理
	 * 	监听kafka消费数据，组装数据结构，批量插入数据库
	 * @param records
	 * @throws Exception
	 */
	public void listenKafka(List<ConsumerRecord<?, ?>> records)  throws Exception;
	
	/**
	 * 	报警数据业务处理
	 * 	监听kafka消费数据，组装数据结构，批量插入数据库
	 * @param records
	 * @throws Exception
	 */
	public void listenKafkaAlarm(List<ConsumerRecord<?, ?>> records)  throws Exception;
	
	/**
	 * 	处理失败的协议 进行补偿业务处理
	 * @throws Exception
	 */
	public void protocolCompensate() throws Exception;

	/**
	 * 	重复协议处理（吴涛去重方案）
	 *	
	 * <p>Title: protocolRepeat</p>  
	 * <p>Description: </p>  
	 * @author zhanghc
	 * @throws Exception
	 */
	public void protocolRepeat() throws Exception;

	/**
	 * 箱体上报数据   _0x0B _0x0C
	 * @param records
	 */
    void listenKafkaReportData(List<ConsumerRecord<?,?>> records);
}
