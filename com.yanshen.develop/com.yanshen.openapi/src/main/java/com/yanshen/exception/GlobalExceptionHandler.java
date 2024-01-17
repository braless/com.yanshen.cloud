package com.yanshen.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import util.MsgInfo;

/**
 * @Auther: @Yanchao
 * @Date: 2023-08-28 14:59
 * @Description:
 * @Location: com.yanshen.exception
 * @Project: com.yanshen.cloud
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(TipException.class)
    public MsgInfo<?> handleTipException(TipException e){
        log.error(e.getMessage(), e);
        return MsgInfo.fail(e.getMessage());
    }

}
