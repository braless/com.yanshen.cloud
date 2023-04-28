package com.yanshen.controller;

import com.yanshen.utils.MsgInfo;
import com.yanshen.entity.User;
import com.yanshen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @BelongsProject: Spring-Boot
 * @BelongsPackage: com.yanshen.controller
 * @Author: cuiyanchao
 * @CreateTime: 2019-06-06 11:25
 * @Description: ${Description}
 */
@RestController
@RequestMapping("/list")
public class ListController {
    @Autowired
    UserService userService;

    @RequestMapping("/getAll")

    public List get(@RequestBody User user) {
        // Model model
        List goodsList = userService.getGoodsList();
        // model.addAttribute("gd",goodsList);
        return goodsList;
    }

    @RequestMapping("/login")
    public MsgInfo<User> login(@RequestBody User user) {
        MsgInfo msgInfo = new MsgInfo();
        User dbuser = userService.login(user);
        List goodsList = userService.getGoodsList();
        if (dbuser == null) {
            return null;
        } else
            msgInfo.setData(goodsList);
        return msgInfo;
    }

}
