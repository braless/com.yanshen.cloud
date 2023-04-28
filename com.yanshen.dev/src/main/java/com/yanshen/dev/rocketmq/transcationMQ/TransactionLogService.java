package com.yanshen.dev.rocketmq.transcationMQ;

import com.yanshen.dev.mapper.TranscationDAO;
import com.yanshen.dev.rocketmq.transcationMQ.bean.TransactionLog;
import com.yanshen.dev.tutils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: cyc
 * @Date: 2023/3/22 14:33
 * @Description:
 */
@Slf4j
@Service
public class TransactionLogService {

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    TranscationDAO transcationDAO;
    public int get(String id){
        String s = redisUtil.get("transcation_" + id);
        log.info("从Redis获取到事务id:{},data:{}",id,s);
        return s.length();
    }
    @Transactional
    public void insertLog(TransactionLog log){
        transcationDAO.insertLog(log);
    }
}
