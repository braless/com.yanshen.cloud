package com.yanshen.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yanshen.entity.SysUser;
import com.yanshen.exception.TipException;
import com.yanshen.mapper.LoginMapper;
import com.yanshen.mapper.SysUserMapper;
import com.yanshen.service.LoginService;
import com.yanshen.utils.PasswordUtil;
import com.yanshen.utils.oConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-12 17:48
 * @Description:
 * @Location: com.yanshen.service.impl
 * @Project: com.yanshen.cloud
 */
@Service
public class LoginServiceImpl extends ServiceImpl<SysUserMapper,SysUser>  implements LoginService{

    @Autowired
    LoginMapper loginMapper;

    public SysUser queryUser(String userName) {
        return loginMapper.queryUser(userName);
    }
    public List<Map<String, Object>> getUserPower(String userName) {
        return loginMapper.getUserPower(userName);
    }

    @Override
    public SysUser register(SysUser sysUser) {

        SysUser dbUser=this.queryUser(sysUser.getUserName());
        if (dbUser!=null){
            throw new TipException("用户名已存在!");
        }
        String salt = oConvertUtils.randomGen(8);
        String passwordEncode = PasswordUtil.encrypt(sysUser.getUserName(), sysUser.getPassword(), salt);
        sysUser.setStatus(0);
        sysUser.setPassword(passwordEncode);
        sysUser.setCreateTime(new Date());
        sysUser.setSalt(salt);
        this.save(sysUser);
        return null;
    }
}
