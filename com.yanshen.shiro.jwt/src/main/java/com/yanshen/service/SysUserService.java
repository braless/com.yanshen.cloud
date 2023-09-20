package com.yanshen.service;

import com.yanshen.common.R;
import com.yanshen.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 知否技术
 * @since 2022-03-09
 */
public interface SysUserService extends IService<SysUser> {

    R login(String username, String password);

}
