package com.yanshen.utils;

public class MsgInfo<T> {
    private String message = "success";
    private String code = "000000"; // 服务号 + 编号, 000000表示成功, 100000表示提示, 200000 表示数据库查询信息提示，300000表示接口异常
    private T data;
    private String provided = "iPhone 12 Mini";

    public String getProvided() {
        return provided;
    }

    public void setProvided(String provided) {
        this.provided = provided;
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

    public MsgInfo(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> MsgInfo<T> success() {
        return new MsgInfo<>();
    }

    public static <T> MsgInfo<T> success(T t) {
        MsgInfo<T> msgInfo = new MsgInfo<>();
        msgInfo.data = t;
        return msgInfo;
    }

    public static <T> MsgInfo<T> fail(String msg) {
        MsgInfo<T> msgInfo = new MsgInfo<>();
        msgInfo.code = "300000";
        msgInfo.message = msg;
        return msgInfo;
    }

    public static <T> MsgInfo<T> fail(String code, String msg) {
        MsgInfo<T> msgInfo = new MsgInfo<>();
        msgInfo.code = code;
        msgInfo.message = msg;
        return msgInfo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "MsgInfo{" +
                "message='" + message + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }
}

