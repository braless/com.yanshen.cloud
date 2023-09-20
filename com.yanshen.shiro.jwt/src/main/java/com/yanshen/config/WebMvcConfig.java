package com.yanshen.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-19 15:39
 * @Description:
 * @Location: com.yanshen.config
 * @Project: com.yanshen.cloud
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void extendHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        resolvers.add(0, new MyHandlerExceptionResolver());
    }

    private static class MyHandlerExceptionResolver implements HandlerExceptionResolver {

        @Override
        public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
            // 你的异常处理逻辑
            return null;
        }
    }
}
