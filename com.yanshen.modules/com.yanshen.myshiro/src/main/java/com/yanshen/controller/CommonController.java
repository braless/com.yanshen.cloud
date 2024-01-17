package com.yanshen.controller;

import com.yanshen.base.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-13 14:54
 * @Description:
 * @Location: com.yanshen.controller
 * @Project: com.yanshen.cloud
 */
@RestController
@RequestMapping("/sys/common")
public class CommonController {

    /**
     * @Author
     * @return
     */
    @GetMapping("/403")
    public Result noauth()  {
        return Result.failed("没有权限，请联系管理员授权");
    }

}
