package com.yanshen.weibo.service;

import com.yanshen.weibo.entity.LyUser;
import com.yanshen.weibo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

   public List<LyUser> getUsers(){
        List<LyUser> users =userMapper.getUser();
        return users;
    }

    public  void update(LyUser user){
       userMapper.update(user);
    }
}
