package com.yanshen.controller;

import com.sun.javafx.scene.layout.region.SliceSequenceConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Fox
 */
@RestController
@RequestMapping("/user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/getCurrentUser")
    public Object getCurrentUser(Authentication authentication) {
        logger.info("进入/user/getCurrentUser方法");

        return authentication.getPrincipal();
    }


}
