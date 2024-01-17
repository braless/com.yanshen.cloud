package com.yanshen.qq;



import filter.ExceptionFilter;
import filter.TokenFilter;
import filter.TraceFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude = RedisAutoConfiguration.class)
//@EnableEurekaClient
@EnableFeignClients
@EnableDiscoveryClient
//@EnableHystrix
//@EnableCircuitBreaker
@EnableConfigurationProperties
public class QQServer1 {
//    @Bean
//    public FilterRegistrationBean traceFilter() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new TraceFilter());// 添加过滤器
//        registration.addUrlPatterns("/*");// 设置过滤路径，/*所有路径
//        registration.setName("traceFilter");// 设置优先级
//        registration.setOrder(3);// 设置优先级
//        return registration;
//    }
//
//    @Bean
//    public FilterRegistrationBean tokenFilterRegistration() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(new TokenFilter());// 添加过滤器
//        registration.addUrlPatterns("/*");// 设置过滤路径，/*所有路径
//        registration.setName("TokenFilter");// 设置优先级
//        registration.setOrder(2);// 设置优先级
//        return registration;
//    }
//    @Bean
//    public FilterRegistrationBean<ExceptionFilter> testFilterRegistration() {
//        FilterRegistrationBean<ExceptionFilter> registration = new FilterRegistrationBean<ExceptionFilter>();
//        registration.setFilter(new ExceptionFilter());// 添加过滤器
//        registration.addUrlPatterns("/*");// 设置过滤路径，/*所有路径
//        registration.setName("ExceptionFilter");// 设置优先级
//        registration.setOrder(1);// 设置优先级
//        return registration;
//    }


    public static void main(String[] args) {
        SpringApplication.run(QQServer1.class, args);
    }

}
