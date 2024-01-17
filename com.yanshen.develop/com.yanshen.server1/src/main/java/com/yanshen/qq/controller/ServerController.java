package com.yanshen.qq.controller;

import base.ApiResult;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.yanshen.qq.rpc.WeiboRpc;
import dto.DevDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import util.ThreadLocalUtils;

import javax.annotation.Resource;
import javax.annotation.Resources;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/server")
// RefreshScope注解是开启动态刷新配置，如果不加这个，在配置中心更改配置后，这里面的配置不会刷新。
@RefreshScope
public class ServerController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${yanshen.ip}")
    private String ip;
    @Value("${server.port}")
    private String port;

    @Value("${yanshen.name}")
    private String name;
    @Resource
    WeiboRpc rpc;

    @RequestMapping("/call")
    public ApiResult get(@RequestBody DevDTO devDTO) {
        String msg = "This Msg from QQ_Server 1 !-----PORT IS :" + port;
        devDTO.setDevName("Msg Forward From Server1");
        logger.info("Server1收到请求:{},转发的请求参数:{}",msg,devDTO);
        ApiResult weibo = rpc.getWeibo(devDTO);
        return ApiResult.success(weibo.getData(),msg);
    }


    @RequestMapping("/info")
//    @HystrixCommand(
//            fallbackMethod = "fallbackMethod",// 服务降级方法
//
//            // 使用commandProperties 可以配置熔断的一些细节信息
//            commandProperties = {
//
//                    // 类似kv形式
//                    //这里这个参数意思是熔断超时时间2s，表示过了多长时间还没结束就进行熔断
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "12000")
//            }
//    )
    public ApiResult r(HttpServletRequest request) throws InterruptedException {
        String ip = request.getHeader("x-forwarded-for");
        logger.warn("当前请求ip是:{}" + ip);
        logger.info("解析到的用户名是:" + ThreadLocalUtils.getCurrentUser());
        logger.info("请求进入了Server 1--------------");
        DevDTO devDTO =new DevDTO();
        devDTO.setDevName("本消息由QQServer1返回");
        return ApiResult.success(devDTO,"本消息由QQServer1返回");
    }

    @RequestMapping("/local")
    public String getConfig() {
        return "current server is qq_server : current ip is:" + port;
    }

    /**
     * https://blog.csdn.net/tianyaleixiaowu/article/details/78772269
     *
     * @param request
     * @return https://jishuin.proginn.com/p/763bfbd33d36  解释了zuul的默认网关超时时间
     */
    public String fallbackMethod(HttpServletRequest request) {
        return "服务出现异常了我是反馈方法";
    }
}
