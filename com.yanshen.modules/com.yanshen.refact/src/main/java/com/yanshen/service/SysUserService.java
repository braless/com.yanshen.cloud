package com.yanshen.service;


import com.yanshen.entity.SysUser;
import com.yanshen.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserService {

    @Autowired
    SysUserMapper userMapper;

    public SysUser getUserByName(String userName){
        return userMapper.getUserByName(userName);
    }
}
