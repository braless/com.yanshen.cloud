package com.yanshen.service;

import com.yanshen.common.Result;
import com.yanshen.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yanshen.entity.dto.LoginDTO;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 知否技术
 * @since 2022-03-09
 */
public interface SysUserService extends IService<SysUser> {

    Result login(LoginDTO loginDTO);

}
