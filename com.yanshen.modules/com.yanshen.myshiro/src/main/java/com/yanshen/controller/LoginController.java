package com.yanshen.controller;

import com.yanshen.base.Result;
import com.yanshen.entity.SysUser;
import com.yanshen.service.LoginService;
import com.yanshen.shiro.JwtUtil;
import com.yanshen.utils.PasswordUtil;
import com.yanshen.utils.oConvertUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-12 17:52
 * @Description:
 * @Location: com.yanshen.controller
 * @Project: com.yanshen.cloud
 */
@RestController
@RequestMapping("/sys")
public class LoginController {

    @Autowired
    LoginService loginService;

    @GetMapping("/login")
    public Result login(@RequestParam("userName") String userName, @RequestParam("password") String password) {
        //添加用户认证信息
        Subject subject = SecurityUtils.getSubject();
        //自己系统的密码加密方式 ,这里简单示例一下MD5
        SysUser sysUser =loginService.queryUser(userName);
        String passwordEncode = PasswordUtil.encrypt(sysUser.getUserName(), password, sysUser.getSalt());
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, passwordEncode);
        if (!passwordEncode.equals(sysUser.getPassword())){
            return Result.failed("账号或密码错误！");
        }
        try {
            //进行验证，AuthenticationException可以catch到,但是AuthorizationException因为我们使用注解方式,是catch不到的,所以后面使用全局异常捕抓去获取
            subject.login(usernamePasswordToken);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return Result.failed("账号或密码错误！");
        } catch (AuthorizationException e) {
            e.printStackTrace();
            return Result.failed("没有权限！");
        }
        String token = JwtUtil.sign(userName);
        Map<String,String> map =new HashMap<>();
        map.put("userName",userName);
        map.put("token",token);
        return Result.success(map,"登录成功!");
    }


    @RequestMapping("/register")
    public Result register(@RequestBody  SysUser sysUser){
        loginService.register(sysUser);
        return Result.success();
    }
}