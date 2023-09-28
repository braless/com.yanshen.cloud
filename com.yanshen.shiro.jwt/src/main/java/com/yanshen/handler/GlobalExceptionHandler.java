package com.yanshen.handler;

import com.yanshen.common.Result;
import io.lettuce.core.RedisConnectionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

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
        log.warn("运行时异常：{}",e.getMessage());
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
        log.error("参数错误");
        String msg = e.getMessage();//.split(" ")[5];
        return Result.fail("参数错误: "+msg);
    }


    @ExceptionHandler(value = RedisConnectionException.class)
    public Result handler(RedisConnectionException e){
        log.error("缓存服务不可用");
        String msg = e.getMessage();//.split(" ")[5];
        return Result.fail("系统异常: 缓存服务不可用");
    }


    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public Result handler(DataIntegrityViolationException e){
        log.error("数据库执行异常:{}",e);
        String msg = e.getMessage().split(" ")[5];
        return Result.fail("数据库执行异常: "+e.getCause().getMessage());
    }

    /**
     * 表不存在异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = BadSqlGrammarException.class)
    public Result handler(BadSqlGrammarException e){
        log.error("数据库执行异常:{}",e);
        String msg = e.getMessage().split(" ")[5];
        return Result.fail("数据库执行异常: "+e.getCause().getMessage());
    }

    /**
     * @Author 不支持方法 判断
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result<?> handler(HttpRequestMethodNotSupportedException e){
        StringBuffer sb = new StringBuffer();
        sb.append("不支持");
        sb.append(e.getMethod());
        sb.append("请求方法，");
        sb.append("支持以下");
        String [] methods = e.getSupportedMethods();
        if(methods!=null){
            for(String str:methods){
                sb.append(str);
                sb.append("、");
            }
        }
        log.error(sb.toString(), e);
        //return Result.error("没有权限，请联系管理员授权");
        return Result.fail(405,sb.toString());
    }

    /**
     * 参数校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handler(MethodArgumentNotValidException e){
        log.error("数据库执行异常:{}",e);
        String r =e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        BindingResult bindingResult = e.getBindingResult();
        String errorMsg = bindingResult.getFieldErrors().stream().map(o -> o.getDefaultMessage()).collect(Collectors.joining(","));
        return Result.fail("参数校验异常: "+errorMsg);
    }



    /**
     * 参数校验异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = BindException.class)
    public Result handler(BindException e){
        log.error("数据库执行异常:{}",e.getMessage());
        String r =e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        BindingResult bindingResult = e.getBindingResult();
        String errorMsg = bindingResult.getFieldErrors().stream().map(o -> o.getDefaultMessage()).collect(Collectors.joining(","));
        return Result.fail("参数校验异常: "+errorMsg);
    }


}
