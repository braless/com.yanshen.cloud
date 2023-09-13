package com.yanshen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yanshen.entity.SysUser;

import java.util.List;
import java.util.Map;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-12 17:47
 * @Description:
 * @Location: com.yanshen.service
 * @Project: com.yanshen.cloud
 */
public interface LoginService extends IService<SysUser> {

    SysUser queryUser(String userName );

    List<Map<String,Object>> getUserPower(String userName);

    SysUser register(SysUser sysUser);
}
