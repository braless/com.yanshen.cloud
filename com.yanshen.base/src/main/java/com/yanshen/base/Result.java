package com.yanshen.base;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;

@Data
public class Result<T> implements Serializable {

    /**
     * 返回码
     */
    private long code;

    /**
     * 返回码描述
     */
    private String msg;

    /**
     * 服务端返回的具体结果数据
     */
    private T data;

    /**
     * 输入字段提示。一般为表单提交时，后端对表单进行整体校验，返回给前端相应字段的校验结果。
     */
    private Map<String, Object> fields;

    public Result() {
    }

    protected Result(long code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success() {
        return new Result(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    public static Result success(String msg) {
        return new Result(ResultCode.SUCCESS.getCode(), msg, null);
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    public static <T> Result<T> success(T data, String msg) {
        return new Result<T>(ResultCode.SUCCESS.getCode(), msg, data);
    }

    public static <T> Result<T> failed(IErrorCode errorCode) {
        return new Result<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static <T> Result<T> failed(String msg) {
        return new Result<T>(ResultCode.FAILED.getCode(), msg, null);
    }

    public static <T> Result<T> failed(long errorCode, String msg) {
        return new Result<T>(errorCode, msg, null);
    }

    public static <T> Result<T> failed(IErrorCode errorCode, String msg) {
        return new Result<T>(errorCode.getCode(), msg, null);
    }

    public static <T> Result<T> failed(ResultCode errorCode) {
        return new Result<T>(errorCode.getCode(), errorCode.getMessage(), null);
    }

    public static <T> Result<T> failed() {
        return failed(ResultCode.FAILED); //ResultCode 枚举操作码
    }

    public static <T> Result<T> failed(String message, String... args) {
        return failed(ResultCode.FAILED.getCode(), String.format(message, args));
    }

    public static <T> Result<T> validateFailed() {
        return failed(ResultCode.VALIDATE_FAILED);
    }

    public static <T> Result<T> validateFailed(String msg) {
        return new Result<T>(ResultCode.VALIDATE_FAILED.getCode(), msg, null);
    }

    public static <T> Result<T> unauthorized(T data) {
        return new Result<T>(ResultCode.UNAUTHORIZED.getCode(), ResultCode.UNAUTHORIZED.getMessage(), data);
    }

    public static <T> Result<T> forbidden(T data) {
        return new Result<T>(ResultCode.FORBIDDEN.getCode(), ResultCode.FORBIDDEN.getMessage(), data);
    }

    public static boolean isSuccess(Result result) {
        return null != result && Objects.equals(ResultCode.SUCCESS.getCode(), result.getCode());
    }

    //自定义异常返回的结果
    public static Result defineError(DefinitionException de){

        return Result.failed(de.errorCode, de.errorMsg);
    }
    //其他异常处理方法返回的结果
    public static Result otherError(Exception e){
        return Result.failed(500, e.getMessage());
    }
}
