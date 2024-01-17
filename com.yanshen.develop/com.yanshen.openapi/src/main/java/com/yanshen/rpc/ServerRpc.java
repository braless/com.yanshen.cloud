package com.yanshen.rpc;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "com-yanshen-server")
public interface ServerRpc {
    @PostMapping("/server/r")
    String getServer();
}
