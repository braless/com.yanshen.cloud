package com.yanshen.service;

import com.yanshen.entity.Goods;
import com.yanshen.entity.User;
import com.yanshen.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @BelongsProject: Spring-Boot
 * @BelongsPackage: com.yanshen.service
 * @Author: cuiyanchao
 * @CreateTime: 2019-06-05 11:34
 * @Description: ${Description}
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    public User Sel(int id) {
        return userMapper.Sel(id);
    }
    public User login(User user) {
        return userMapper.login(user);
    }
    public int register(User user) {
        return userMapper.register(user);
    }

    public List getGoodsList() {
        return userMapper.getGoodsList();
    }


}
