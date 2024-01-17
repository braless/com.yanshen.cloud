package com.yanshen.rpc;

import base.ApiResult;
import dto.DevDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "com.yanshen.dev")
public interface WeiboRpc {

    @PostMapping("/dev/something")
    ApiResult getWeibo(DevDTO devDTO);
}
