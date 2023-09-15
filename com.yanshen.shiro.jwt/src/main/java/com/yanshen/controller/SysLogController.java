package com.yanshen.controller;

import cn.hutool.json.JSONObject;

import com.yanshen.common.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-15 10:54
 * @Description:
 * @Location: com.zhifou.controller
 * @Project: shiro_jwt
 */
@RestController
@RequestMapping("/sys")
public class SysLogController {
    //注解验角色和权限
    //@RequiresRoles("common")
    @RequestMapping("/log")
    public Result queryLog(){
        String msg ="您可以访问日志系统!";
        return Result.success(new JSONObject().set("tips",msg));
    }
}
