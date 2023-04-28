package com.yanshen.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MsgInfo<T> {
    private String message = "success";
    private String code = "000000"; // 服务号 + 编号, 000000表示成功, 100000表示提示, 200000 表示数据库查询信息提示，300000表示接口异常
    private T data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public MsgInfo() {
    }

    public MsgInfo(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return "000000".equals(code);
    }

    public MsgInfo(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
