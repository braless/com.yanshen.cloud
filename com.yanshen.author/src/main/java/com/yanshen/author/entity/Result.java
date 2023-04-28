package com.yanshen.author.entity;

/**
 * <h3>spring-cloud</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2022-03-01 14:31
 **/
public class Result<T> {

    private String code;

    private String message;

    private T data;

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
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

    public boolean isSuccess() {
        return this.code.equals(successCode);
    }

    public static String successCode = "000000";

    public static String failCode = "100000";

    public static String successMsg = "成功";

    public static String failMsg = "失败";

    public static<T> Result<T> success() {
        Result<T> response = new Result<T>();
        response.setCode(successCode);
        response.setData(null);
        response.setMessage(successMsg);
        return response;
    }

    public static<T> Result<T> success(T data,String msg) {
        Result<T> response = new Result<T>();
        response.setCode(successCode);
        response.setData(data);
        response.setMessage(msg!=null?msg:successMsg);
        return response;
    }

    public static<T> Result<T> fail() {
        Result<T> response = new Result<T>();
        response.setCode(failCode);
        response.setData(null);
        response.setMessage(failMsg);
        return response;
    }

    public static<T> Result<T> fail(T data,String msg,String code) {
        Result<T> response = new Result<T>();
        response.setCode(code==null?failCode:code);
        response.setData(data);
        response.setMessage(msg!=null?msg:failMsg);
        return response;
    }
}

