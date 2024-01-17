package com.yanshen.dev.rocketmq;

import lombok.Data;

@Data
public class RocketMsgVo<T> {

    /**
     * 主题
     */
    private String topic;
    /**
     * 标签
     */
    private String tag;

    /**
     * 命令编号
     */
    private String command;

    /**
     * 时间戳
     */
    private long timestamp = System.currentTimeMillis();

    /**
     * 请求参数
     */
    private T param;

    /**
     * 签名
     */
    private String sign;

    @Override
    public String toString() {
        return "{" +
                "topic='" + topic + '\'' +
                ", tag='" + tag + '\'' +
                ", param=" + param +
                '}';
    }
}
