package com.yanshen.mapper;

import com.yanshen.entity.SysPermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @auther cyc
 * @create 2023-09-21
 * @describe 菜单权限表mapper类
 */
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /**
     *   根据用户查询用户权限
     */
    public List<SysPermission> queryByUser(@Param("username") String username);

}
