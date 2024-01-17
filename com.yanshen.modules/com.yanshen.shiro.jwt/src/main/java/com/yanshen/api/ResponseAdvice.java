package com.yanshen.api;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-22 09:09
 * @Description:
 * @Location: com.yanshen.api
 * @Project: com.yanshen.cloud
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanshen.common.Result;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author Yanshen
 * @date 2023/9/22 10:10 上午
 * https://blog.51cto.com/u_15323393/3289327
 * 以下备用
 * https://www.cnblogs.com/sw-code/p/13956522.html
 */
@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    @SneakyThrows
    public Object beforeBodyWrite(Object o, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass,
                                  ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if(o instanceof String){
            return objectMapper.writeValueAsString(Result.success(o));
        }
        //如果是Result 直接返回
        if(o instanceof Result){
            return o;
        }
        return Result.success(o);
    }
}
