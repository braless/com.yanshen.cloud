package com.yanshen.controller;
import com.auth0.jwt.interfaces.Claim;
import com.yanshen.entity.User;
import com.yanshen.service.UserService;
import com.yanshen.utils.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.Map;

/**
 * @BelongsProject: Spring-Boot
 * @BelongsPackage: com.yanshen.controller
 * @Author: cuiyanchao
 * @CreateTime: 2019-06-05 14:07
 * @Description: ${Description}
 */
@Controller
@RequestMapping("/user")
public class QLoginController {
    @Autowired
    private UserService userService;
    private final Logger logger = LoggerFactory.getLogger(QLoginController.class);

    //跳转首页（登录页）
    @RequestMapping("/toIndex")
    public String show() {
        return "index";
    }

    //登录操作
    @ResponseBody
    @RequestMapping("/loginUser")
    public String login(User user, HttpServletRequest request) throws Exception {

        User u1 = userService.login(user);

        if (u1 == null) {
            return "用户名或密码错误";
        } else {
            //登录成功后将用户放入session中，用于拦截
            request.getSession().setAttribute("session_user", user);
            String token = JWTUtils.createToken(user.getUserName());
            logger.info("生成token:  " + token);

            Map<String, Claim> map = JWTUtils.verifyToken(token);

            logger.info("解析token:  " + map.get("aud").asString());
            return "登录成功";
        }
    }

    //跳转注册页
    @RequestMapping("/toRegister")
    public String toRegister() {
        return "register";
    }

    //注册操作
    @RequestMapping("/register")
    public String register(User user, HttpServletRequest request) {
        int su = userService.register(user);
        request.getSession().setAttribute("session_user", user);
        if (su == 0) {
            System.out.println("----");
        }
        return "welcome";
    }

    //测试未登陆拦截页面
    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

    //退出登录
    @RequestMapping("/outUser")
    public void outUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("session_user");
        response.sendRedirect("/user/toIndex");
    }

}
