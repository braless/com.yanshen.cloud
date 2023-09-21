package com.yanshen.api;

import java.util.Set;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-21 10:29
 * @Description:
 * @Location: com.yanshen.api
 * @Project: com.yanshen.cloud
 */
public interface CommonAPI {

    /**
     * 1查询用户角色信息
     * @param username
     * @return
     */
    Set<String> queryUserRoles(String username);


    /**
     * 2查询用户权限信息
     * @param username
     * @return
     */
    Set<String> queryUserAuths(String username);
}
