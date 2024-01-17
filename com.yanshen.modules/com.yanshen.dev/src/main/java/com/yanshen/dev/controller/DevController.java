package com.yanshen.dev.controller;

import com.yanshen.dev.base.ApiResult;
import dto.DevDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 开发的测试
 *
 * @Auther: cyc
 * @Date: 2023/3/27 16:47
 * @Description: 开发的测试
 */
@RequestMapping("/dev")
@RestController
@Slf4j
public class DevController {

    @RequestMapping("/something")
    public ApiResult getInfo(@RequestBody DevDTO devDTO){
        log.info("Dev收到消息: {} 请求来自: {}",devDTO,devDTO.getDevName());
        devDTO.setDevCode("Dev服务处理过这条消息了!" +
                "" +
                "");
        return ApiResult.success(devDTO,"这条消息来自: dev");
    }
}
