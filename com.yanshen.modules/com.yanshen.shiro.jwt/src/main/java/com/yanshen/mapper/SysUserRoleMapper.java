package com.yanshen.mapper;

import com.yanshen.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @auther cyc
 * @create 2023-09-21
 * @describe 用户角色表mapper类
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {


    @Select("select role_code from sys_role where id in (select role_id from sys_user_role where user_id = (select id from sys_user where username=#{username}))")
    List<String> getRoleByUserName(@Param("username") String username);

    @Select("select id from sys_role where id in (select role_id from sys_user_role where user_id = (select id from sys_user where username=#{username}))")
    List<String> getRoleIdByUserName(@Param("username") String username);

}
