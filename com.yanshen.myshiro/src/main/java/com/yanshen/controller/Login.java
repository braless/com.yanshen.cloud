package com.yanshen.controller;


import com.yanshen.base.Result;
import com.yanshen.database.UserBean;
import com.yanshen.database.UserService;
import com.yanshen.entity.User;
import com.yanshen.exception.TipException;
import com.yanshen.shiro.JwtUtil;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class Login {

    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public Result getInfo() {

        if (true) {
            throw new TipException("返回自定义异常咯");
        }

        return Result.success();
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user) {
        UserBean userBean = userService.getUser(user.getUserName());
        String password =user.getPassWord();
        String username =user.getUserName();
        if (userBean.getPassword().equals(password)) {
            return Result.success(JwtUtil.sign(username, password));
        } else {
            throw new UnauthorizedException();
        }
    }
}
