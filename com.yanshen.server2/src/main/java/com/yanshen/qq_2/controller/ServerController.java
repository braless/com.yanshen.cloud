package com.yanshen.qq_2.controller;

import base.ApiResult;
import com.yanshen.qq_2.rpc.WeiboRpc;
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

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/server")
// RefreshScope注解是开启动态刷新配置，如果不加这个，在配置中心更改配置后，这里面的配置不会刷新。
@RefreshScope
public class ServerController {
    @Autowired
    WeiboRpc rpc;

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${server.port}")
    private String port;


    @RequestMapping("/info")
    public ApiResult r(HttpServletRequest request){
        String ip =request.getHeader("x-forwarded-for");
        logger.warn("当前请求ip是:{}",ip);
        logger.info("解析到的用户名是:"+ ThreadLocalUtils.getCurrentUser());
        DevDTO devDTO =new DevDTO();
        devDTO.setDevName("本消息由QQServer2返回");
        return ApiResult.success(devDTO,"本消息由QQServer2返回");
    }


    @RequestMapping("/call")
    public ApiResult get(@RequestBody DevDTO devDTO) {
        String msg = "This Msg from QQ_Server 2 !-----PORT IS :" + port;
        devDTO.setDevName("Msg Forward From Server2");
        logger.info("Server2收到请求:{},转发的请求参数:{}",msg,devDTO);
        ApiResult weibo = rpc.getWeibo(devDTO);
        return ApiResult.success(weibo.getData(),msg);
    }

    @RequestMapping("/local")
    public String getConfig() {
        return "current server is qq_server : current ip is:" + port;
    }
}
