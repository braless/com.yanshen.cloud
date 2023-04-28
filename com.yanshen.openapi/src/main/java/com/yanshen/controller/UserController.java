package com.yanshen.controller;

import com.yanshen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject: Spring-Boot
 * @BelongsPackage: com.yanshen.controller
 * @Author: cuiyanchao
 * @CreateTime: 2019-06-05 11:32
 * @Description: ${Description}
 */
@RestController
@RequestMapping("/get")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/getUser/{id}")
    public String GetUser(@PathVariable int id) {
        return userService.Sel(id).toString();
    }


}
