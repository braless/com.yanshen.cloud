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
public class Result<T> implements Serializable {

    private Integer code;
    private String message;
    private long timestamp;
    private T Data;

    public static Result success(Object object){
        Result result = new Result();
        result.setCode(200);
        result.setData(object);
        result.setTimestamp(System.currentTimeMillis());
        result.setMessage("操作成功");
        return  result;
    }
    public static Result success(){
        Result result = new Result();
        result.setCode(200);
        result.setData(null);
        result.setTimestamp(System.currentTimeMillis());
        result.setMessage("操作成功");
        return  result;
    }

    public static Result fail(String message){
        Result result = new Result();
        result.setCode(ResultCodeEnum.PARAM_ERROR.getCode());
        result.setData(null);
        result.setMessage(message);
        result.setTimestamp(System.currentTimeMillis());
        return  result;
    }
    public static Result fail(Integer code,String message){
        Result result = new Result();
        result.setCode(code);
        result.setData(null);
        result.setMessage(message);
        result.setTimestamp(System.currentTimeMillis());
        return  result;
    }







}
