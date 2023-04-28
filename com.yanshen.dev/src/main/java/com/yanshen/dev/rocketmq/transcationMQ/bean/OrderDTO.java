package com.yanshen.dev.rocketmq.transcationMQ.bean;

import lombok.Data;

/**
 * @Auther: cyc
 * @Date: 2023/3/22 14:26
 * @Description:
 */
@Data
public class OrderDTO {

    private Long Id;
    private String orderNo;
    private String commodityCode;
    private String timeStr;
}
