package com.yanshen.common;

import com.yanshen.enums.ResultCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @Desc: 处理统一返回结果格式
 * @Author: 知否技术
 * @date: 下午9:44 2022/10/24
 */
@Data
public class R<T> implements Serializable {

    private Integer code;
    private String message;
    private long timestamp;
    private T Data;

    public static R success(Object object){
        R r = new R();
        r.setCode(200);
        r.setData(object);
        r.setTimestamp(System.currentTimeMillis());
        r.setMessage("操作成功");
        return r;
    }
    public static R success(){
        R r = new R();
        r.setCode(200);
        r.setData(null);
        r.setTimestamp(System.currentTimeMillis());
        r.setMessage("操作成功");
        return r;
    }

    public static R fail(String message){
        R r = new R();
        r.setCode(ResultCodeEnum.PARAM_ERROR.getCode());
        r.setData(null);
        r.setMessage(message);
        r.setTimestamp(System.currentTimeMillis());
        return r;
    }
    public static R fail(Integer code, String message){
        R result = new R();
        result.setCode(code);
        result.setData(null);
        result.setMessage(message);
        result.setTimestamp(System.currentTimeMillis());
        return  result;
    }







}
