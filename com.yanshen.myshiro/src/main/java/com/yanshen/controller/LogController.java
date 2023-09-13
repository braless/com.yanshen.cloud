package com.yanshen.controller;

import com.yanshen.base.Result;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-13 11:14
 * @Description:
 * @Location: com.yanshen.controller
 * @Project: com.yanshen.cloud
 */
@RestController
@RequestMapping("/sys")
public class LogController {

    //注解验角色和权限
    //@RequiresRoles("common")
    @RequestMapping("/querySystemLog")
    public Result queryLog() {
       return Result.success("您可以查询日志");
    }
}
