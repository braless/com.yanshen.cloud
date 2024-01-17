package com.yanshen.dev.rocketmq.transcationMQ.bean;

import lombok.Data;

/**
 * @Auther: cyc
 * @Date: 2023/3/22 15:21
 * @Description:
 */
@Data
public class TransactionLog {

    private String id;
    private String transcationId;
    private String msg;
    private String  business;
    private String foreignKey;
}
