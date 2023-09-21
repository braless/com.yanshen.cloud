package com.yanshen.controller;


import com.yanshen.common.R;
import com.yanshen.entity.SysUser;
import com.yanshen.service.SysUserService;
import com.yanshen.util.BcryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 知否技术
 * @since 2022-03-09
 */
@RestController
@RequestMapping("/user")
public class SysUserController {

    @Autowired
    private SysUserService userService;


    @GetMapping("/list")
    public R list(){
       return R.success( userService.list());
    }

    @PostMapping("/register")
    public R<SysUser> register(@RequestBody SysUser sysUser) {
        sysUser.setPassword(BcryptUtil.encode(sysUser.getPassword()));
        userService.save(sysUser);
        return R.success(sysUser);
    }

    @PostMapping("/login")
    public R login(@RequestParam String username, @RequestParam String password) {
        // 从数据库中查找用户的信息，信息正确生成token
        return userService.login(username, password);
    }

    @GetMapping("/detail")
    public R detail(@RequestParam String id) {
        try {
            return R.success(userService.getById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return R.fail(e.getMessage());
        }
    }
}
