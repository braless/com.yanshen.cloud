package com.yanshen.handler;

import com.yanshen.common.Result;
import io.lettuce.core.RedisConnectionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

/**
 * @Desc: 全局异常处理器
 * @Author: 知否技术
 * @date: 下午10:37 2022/10/24
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public Result handler(RuntimeException e){
        e.printStackTrace();
        log.info("运行时异常：",e.getMessage());
        return Result.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = ShiroException.class)
    public Result handler(ShiroException e) {
        log.error("运行时异常：----------------{}", e);
        return Result.fail( e.getMessage());
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public Result handler(UnauthorizedException e){
        log.error("没有相关权限");
        String msg = e.getMessage().split(" ")[5];
        return Result.fail("没有相关权限: "+msg);
    }
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public Result handler(MissingServletRequestParameterException e){
        log.error("没有相关权限");
        String msg = e.getMessage();//.split(" ")[5];
        return Result.fail("参数错误: "+msg);
    }


    @ExceptionHandler(value = RedisConnectionException.class)
    public Result handler(RedisConnectionException e){
        log.error("没有相关权限");
        String msg = e.getMessage();//.split(" ")[5];
        return Result.fail("系统异常: 缓存服务不可用");
    }


}
