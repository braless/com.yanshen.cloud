package com.yanshen.controller;


import com.yanshen.common.Result;
import com.yanshen.entity.User;
import com.yanshen.service.UserService;
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
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result<User> register(@RequestBody User user) {
        user.setPassword(BcryptUtil.encode(user.getPassword()));
        userService.save(user);
        return Result.success(user);
    }

    @PostMapping("/login")
    public Result login(@RequestParam String username, @RequestParam String password) {
        // 从数据库中查找用户的信息，信息正确生成token
        return userService.login(username, password);
    }

    @GetMapping("/detail")
    public Result detail(@RequestParam String id) {
        try {
            return Result.success(userService.getById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(e.getMessage());
        }
    }
}
