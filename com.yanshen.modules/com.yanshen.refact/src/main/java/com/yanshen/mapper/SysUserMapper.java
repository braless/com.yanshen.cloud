package com.yanshen.mapper;

import com.yanshen.entity.SysUser;
import org.apache.ibatis.annotations.Param;

public interface SysUserMapper {

    SysUser getUserByName(@Param("username")String userName);
}
