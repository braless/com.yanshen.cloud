package com.yanshen.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yanshen.entity.SysUser;
import com.yanshen.entity.SysUserDTO;

/**
 * @Auther: @Yanchao
 * @Date: 2023-08-28 11:56
 * @Description:
 * @Location: com.yanshen.service
 * @Project: com.yanshen.cloud
 */
public interface SysUserService extends IService<SysUser> {

    SysUserDTO login(SysUser sysUser);
    SysUser regeister(SysUser sysUser);
}
