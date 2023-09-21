package com.yanshen.service;

import com.yanshen.api.CommonAPI;

import java.util.Set;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-21 10:30
 * @Description:
 * @Location: com.yanshen.service
 * @Project: com.yanshen.cloud
 */
public interface ISysBaseAPI extends CommonAPI {

    /**
     * 31获取用户的角色集合
     * @param username
     * @return
     */
    Set<String> getUserRoleSet(String username);

    /**
     * 32获取用户的权限集合
     * @param username
     * @return
     */
    Set<String> getUserPermissionSet(String username);

}
