package com.yanshen.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yanshen.common.PageData;
import com.yanshen.common.R;
import com.yanshen.entity.LoginUser;
import com.yanshen.entity.SysLog;
import com.yanshen.service.SysLogService;
import com.yanshen.util.ThreadLocalUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author @Yanchao
 * @since 2023-09-15
 */
@RestController
@RequestMapping("/sys-log")
public class SysLogController {
    @Autowired
    SysLogService sysLogService;


    @RequestMapping("/page")
    public R<PageData<SysLog>> queryPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize ){
        Page<SysLog> page =new Page<>(pageNum,pageSize);
        LambdaQueryWrapper<SysLog> query=new LambdaQueryWrapper<>();
        query.likeRight(SysLog::getReqUrl,"url-6");
        Page<SysLog> data = sysLogService.page(page,query);
        //PageData<SysLog> pageData =new PageData<>(data.getRecords(), data.getTotal(),pageNum,pageSize);
        return R.success(new PageData<>(data));

    }

    //@WebAuth
    @RequestMapping("/getMe")
    public R getUser(){
        LoginUser sysUser = (LoginUser)SecurityUtils.getSubject().getPrincipal();
        String currentUserId = ThreadLocalUtils.getCurrentUserId();
        return R.success(currentUserId);
    }
}
