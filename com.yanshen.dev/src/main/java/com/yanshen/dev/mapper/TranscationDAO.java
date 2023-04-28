package com.yanshen.dev.mapper;

import com.yanshen.dev.rocketmq.transcationMQ.bean.TransactionLog;

/**
 * <h3>spring-cloud</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2022-03-18 16:59
 **/
public interface TranscationDAO {

    void update();

    void setBupdate();

    //@Transactional
    void updateBytrans();

    void insertLog(TransactionLog log);
}
