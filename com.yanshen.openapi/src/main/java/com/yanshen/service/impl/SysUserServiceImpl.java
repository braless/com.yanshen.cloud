package com.yanshen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanshen.entity.SysUser;
import com.yanshen.entity.SysUserDTO;
import com.yanshen.exception.TipException;
import com.yanshen.mapper.SysUserMapper;
import com.yanshen.service.SysUserService;
import com.yanshen.utils.PasswordUtil;
import com.yanshen.utils.oConvertUtils;
import org.springframework.stereotype.Service;
import util.TokenUtil;

import java.util.Date;

/**
 * @Auther: @Yanchao
 * @Date: 2023-08-28 11:57
 * @Description:
 * @Location: com.yanshen.service.impl
 * @Project: com.yanshen.cloud
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {


    public SysUserDTO login(SysUser sysUser){

        LambdaQueryWrapper<SysUser> query =new LambdaQueryWrapper<>();
        query.eq(SysUser::getUserName,sysUser.getUserName());
        SysUser dbUser=this.getOne(query);
        if (dbUser==null){
            throw new TipException("账号不存在!");
        }
        String passwordEncode = PasswordUtil.encrypt(sysUser.getUserName(), sysUser.getPassword(), dbUser.getSalt());
        if (!passwordEncode.equals(dbUser.getPassword())){
            throw new TipException("账号密码不正确!");
        }
        SysUserDTO userDTO=new SysUserDTO();
        userDTO.setUserName(dbUser.getUserName());
        String token = TokenUtil.createToken(userDTO.getUserName());
        userDTO.setToken(token);
        return userDTO;

    }

    public void reSetPwd(SysUser sysUser){

        //验证当前密码
        String salt = oConvertUtils.randomGen(8);
        sysUser.setSalt(salt);
        String passwordEncode = PasswordUtil.encrypt(sysUser.getUserName(), sysUser.getPassword(), salt);
        sysUser.setPassword(passwordEncode);
    }

    public void forgetPwd(){

    }

    public SysUser regeister(SysUser sysUser){
        String salt = oConvertUtils.randomGen(8);
        String passwordEncode = PasswordUtil.encrypt(sysUser.getUserName(), sysUser.getPassword(), salt);
        sysUser.setStatus(0);
        sysUser.setPassword(passwordEncode);
        sysUser.setCreateTime(new Date());
        sysUser.setSalt(salt);
        this.save(sysUser);
        return sysUser;
    }
}
