package com.yanshen.service.impl;

import cn.hutool.core.util.StrUtil;
import com.yanshen.entity.SysPermission;
import com.yanshen.entity.SysUserRole;
import com.yanshen.mapper.SysPermissionMapper;
import com.yanshen.mapper.SysUserRoleMapper;
import com.yanshen.service.ISysBaseAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-21 10:30
 * @Description:
 * @Location: com.yanshen.service.impl
 * @Project: com.yanshen.cloud
 */
@Slf4j
public class SysBaseApiImpl implements ISysBaseAPI {

    @Autowired
    SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    SysPermissionMapper sysPermissionMapper;
    @Override
    public Set<String> queryUserRoles(String username) {
        return getUserRoleSet(username);
    }

    @Override
    public Set<String> queryUserAuths(String username) {
        return null;
    }


    /**
     * 查询用户拥有的角色集合
     * @param username
     * @return
     */
    @Override
    public Set<String> getUserRoleSet(String username) {
        // 查询用户拥有的角色集合
        List<String> roles = sysUserRoleMapper.getRoleByUserName(username);
        log.info("-------通过数据库读取用户拥有的角色Rules------username： " + username + ",Roles size: " + (roles == null ? 0 : roles.size()));
        return new HashSet<>(roles);
    }
    /**
     * 查询用户拥有的权限集合
     * @param username
     * @return
     */
    @Override
    public Set<String> getUserPermissionSet(String username) {
        Set<String> permissionSet = new HashSet<>();
        List<SysPermission> permissionList = sysPermissionMapper.queryByUser(username);
        for (SysPermission po : permissionList) {
//			// TODO URL规则有问题？
//			if (oConvertUtils.isNotEmpty(po.getUrl())) {
//				permissionSet.add(po.getUrl());
//			}
            if (StrUtil.isNotEmpty(po.getPerms())) {
                permissionSet.add(po.getPerms());
            }
        }
        log.info("-------通过数据库读取用户拥有的权限Perms------username： "+ username+",Perms size: "+ (permissionSet==null?0:permissionSet.size()) );
        return permissionSet;
    }
}
