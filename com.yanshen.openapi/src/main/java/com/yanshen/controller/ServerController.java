package com.yanshen.controller;

import com.yanshen.rpc.ServerRpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server")
public class ServerController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ServerRpc serverRpc;
    @RequestMapping("/getServer")
    public String getServer(){
        logger.info("获取了服务器列表");
        return serverRpc.getServer();
    }
}
