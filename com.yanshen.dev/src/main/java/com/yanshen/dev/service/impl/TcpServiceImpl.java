package com.yanshen.dev.service.impl;

import com.alibaba.fastjson.JSONObject;

import com.yanshen.dev.service.TcpService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 设备接入，E0协议业务处理
 * 
 * @author zhanghuicheng
 *
 */
@Service
public class TcpServiceImpl implements TcpService {

	private final Logger logger = LoggerFactory.getLogger(TcpServiceImpl.class);

	public void listenKafka(List<ConsumerRecord<?, ?>> records) throws Exception {

		List<JSONObject> gar_Recyclable_list = new ArrayList<>();// 投放数据
		List<JSONObject> recycle_loseweight_list = new ArrayList<>();// 减重投放数据
		List<JSONObject> gar_Recyclable_annormal_list = new ArrayList<>();// 投放异常数据
		List<JSONObject> gar_GarIntChange_list = new ArrayList<>();// 积分表数据
		List<JSONObject> gar_GarIntChange_jl_list = new ArrayList<>();// 奖励积分表数据
		List<JSONObject> gar_integral_account_list = new ArrayList<>();// 用户积分变更
		List<JSONObject> gar_message_list = new ArrayList<>();// 消息数据

		List<JSONObject> notice_list = new ArrayList<>();//notice 消息通知数据


		List<String> repeatMongoDbIdList = new ArrayList<>();// 存放重复协议的MongoDbId
		List<String> failMongoDbIdList = new ArrayList<>();// 存放处理失败的MongoDbId
		List<String> mongoDbIdList = new ArrayList<>();// 存放处理完毕的MongoDbId

		List<String> dataIds = new ArrayList<>();//
		List<String> dataids_annormal = new ArrayList<>();//

		/*
		风控
		 */
		List<Map<String, Object>> riskParamDataMapList_02 = new ArrayList<>();
		List<Map<String, Object>> riskParamDataMapList_03 = new ArrayList<>();

	}

	/**
	 * 处理失败的协议 进行补偿业务处理 逻辑：1、根据当前时间，查询mongoDb数据中 状态为0（失败）的数据，
	 * 2、循环处理业务（注意：插入积分数据表时，捕获主键重复的异常； 如果有异常，则表示积分数据已经插入，则不修改用户积分变更数据；否则，修改用户积分变更数据；
	 * 因为在第一次处理时，插入积分表数据和修改用户积分数据在同一个事务中）
	 */
	@Override
	public void protocolCompensate() throws Exception {

		/*
		 * startTime ：当天的开始时间
		 * endTime ：当前时间的五分钟之前的时间
		 */
		//获取当前时间5分钟之前的时间点
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar beforeTime = Calendar.getInstance();
		beforeTime.add(Calendar.MINUTE, -60);// 3分钟之前的时间
		String endTime = formatter.format(beforeTime.getTime());

		//获取昨天开始时间
		Calendar currentDate = new GregorianCalendar();
		currentDate.set(Calendar.HOUR_OF_DAY, -24);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		String startTime = formatter.format((Date)currentDate.getTime().clone());
		//远程查询mongo数据（只查询符合条件的一条），查询出来数据后再mongo(dao服务)去除重复后，返回数据
		//DeviceOriginalData deviceOriginalData= this.deviceDataMongoRPC.getDeviceOriginalByOne(0, startTime, endTime);
		//DeviceOriginalData deviceOriginalData= this.deviceDataMongoRPC.getDeviceOriginalByOne(0, "2018-12-16 00:00:00", "2018-12-17 17:30:00");
		//startTime = "2019-01-19 00:00:00";
		//endTime = "2019-01-19 23:59:59";
	}


	/**
	 * 报警数据业务处理
	 */
	@Override
	public void listenKafkaAlarm(List<ConsumerRecord<?, ?>> records) throws Exception {

	}

	/**
	 * 	重复数据处理，去重方案
	 */
	@Override
	public void protocolRepeat() throws Exception {
	}
	/**
	 * 箱体上报数据   _0x0B _0x0C _0x10
	 * @param records
	 */
	@Override
	public void listenKafkaReportData(List<ConsumerRecord<?, ?>> records) {

	}

}
