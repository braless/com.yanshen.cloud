package com.yanshen.service;

import com.yanshen.common.Result;
import com.yanshen.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 知否技术
 * @since 2022-03-09
 */
public interface UserService extends IService<User> {

    Result login(String username, String password);

}
