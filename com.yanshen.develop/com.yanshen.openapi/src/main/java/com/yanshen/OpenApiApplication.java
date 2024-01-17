package com.yanshen;


import com.yanshen.utils.IdWorker;
import filter.ExceptionFilter;
import filter.TokenFilter;
import filter.TraceFilter;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {"com.yanshen.*"})
@EnableFeignClients(basePackages = {"com.yanshen.*"})
@MapperScan("com.yanshen.mapper")
@EnableEurekaClient
public class OpenApiApplication {
    @Value("${idworker.datacenter:0}") //默认1
    private Integer datacenter;
    @Value("${idworker.worker:1}") //默认1
    private Integer worker;

    @Bean
    public FilterRegistrationBean tokenFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new TokenFilter());// 添加过滤器
        registration.addUrlPatterns("/*");// 设置过滤路径，/*所有路径
        registration.setName("TokenFilter");// 设置优先级
        registration.setOrder(2);// 设置优先级
        return registration;
    }
    @Bean
    public FilterRegistrationBean traceFilter() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new TraceFilter());// 添加过滤器
        registration.addUrlPatterns("/*");// 设置过滤路径，/*所有路径
        registration.setName("traceFilter");// 设置优先级
        registration.setOrder(3);// 设置优先级
        return registration;
    }
    @Bean
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(400);
        cm.setDefaultMaxPerRoute(200);
        return cm;
    }

    @Bean
    public HttpClientBuilder httpClientBuilder(HttpClientConnectionManager connManager) {
        RequestConfig config = RequestConfig.custom().setConnectTimeout(3000).setSocketTimeout(1500).setConnectionRequestTimeout(1500).build();
        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setConnectionManager(connManager);
        builder.setDefaultRequestConfig(config);
        builder.setRetryHandler(new DefaultHttpRequestRetryHandler(0, false));
        return builder;
    }
    public static void main(String[] args) {
        SpringApplication.run(OpenApiApplication.class,args);
    }
    @Bean
    public IdWorker getIdWorker() {
        return new IdWorker(worker, datacenter, 1);
    }

    /**
     * 异常捕获过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean<ExceptionFilter> testFilterRegistration() {
        FilterRegistrationBean<ExceptionFilter> registration = new FilterRegistrationBean<ExceptionFilter>();
        registration.setFilter(new ExceptionFilter());// 添加过滤器
        registration.addUrlPatterns("/*");// 设置过滤路径，/*所有路径
        registration.setName("ExceptionFilter");// 设置优先级
        registration.setOrder(1);// 设置优先级
        return registration;
    }
}
