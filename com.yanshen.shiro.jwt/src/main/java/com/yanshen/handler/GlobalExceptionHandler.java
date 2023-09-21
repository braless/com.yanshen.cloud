package com.yanshen.handler;

import com.yanshen.common.R;
import io.lettuce.core.RedisConnectionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;

/**
 * @Desc: 全局异常处理器
 * @Author: 知否技术
 * @date: 下午10:37 2022/10/24
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public R handler(RuntimeException e){
        e.printStackTrace();
        log.info("运行时异常：",e.getMessage());
        return R.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = ShiroException.class)
    public R handler(ShiroException e) {
        log.error("运行时异常：----------------{}", e);
        return R.fail( e.getMessage());
    }

    @ExceptionHandler(value = UnauthorizedException.class)
    public R handler(UnauthorizedException e){
        log.error("没有相关权限");
        String msg = e.getMessage().split(" ")[5];
        return R.fail("没有相关权限: "+msg);
    }
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public R handler(MissingServletRequestParameterException e){
        log.error("没有相关权限");
        String msg = e.getMessage();//.split(" ")[5];
        return R.fail("参数错误: "+msg);
    }


    @ExceptionHandler(value = RedisConnectionException.class)
    public R handler(RedisConnectionException e){
        log.error("没有相关权限");
        String msg = e.getMessage();//.split(" ")[5];
        return R.fail("系统异常: 缓存服务不可用");
    }


    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public R handler(DataIntegrityViolationException e){
        log.error("数据库执行异常:{}",e);
        String msg = e.getMessage().split(" ")[5];
        return R.fail("数据库执行异常: "+e.getCause().getMessage());
    }

    /**
     * 表不存在异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = BadSqlGrammarException.class)
    public R handler(BadSqlGrammarException e){
        log.error("数据库执行异常:{}",e);
        String msg = e.getMessage().split(" ")[5];
        return R.fail("数据库执行异常: "+e.getCause().getMessage());
    }



}
