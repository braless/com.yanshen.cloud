package com.yanshen.controller;

import com.yanshen.entity.SysUser;
import com.yanshen.entity.SysUserDTO;
import com.yanshen.exception.TipException;
import com.yanshen.service.SysUserService;
import com.yanshen.utils.MsgInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: @Yanchao
 * @Date: 2023-08-28 14:25
 * @Description:
 * @Location: com.yanshen.controller
 * @Project: com.yanshen.cloud
 */

@RestController
@RequestMapping("/sys")
public class SysLoginController {


    @Autowired
    SysUserService sysUserService;

    @PostMapping("/login")
    public MsgInfo login(@RequestBody SysUser sysUser){
        MsgInfo msgInfo =new MsgInfo();

        SysUserDTO login = sysUserService.login(sysUser);
        if (login==null){
            throw new TipException("SOrry");
        }
        msgInfo.setData(login);
        return msgInfo;
    }

    @PostMapping("/register")
    public MsgInfo register(@RequestBody SysUser sysUser){
        MsgInfo msgInfo =new MsgInfo();
        sysUserService.regeister(sysUser);
        msgInfo.setMessage("注册成功!");
        return msgInfo;
    }
}
