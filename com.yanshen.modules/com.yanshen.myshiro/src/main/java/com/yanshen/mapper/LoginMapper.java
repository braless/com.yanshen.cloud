package com.yanshen.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yanshen.entity.SysUser;

import java.util.List;
import java.util.Map;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-12 17:59
 * @Description:
 * @Location: com.yanshen.mapper
 * @Project: com.yanshen.cloud
 */
public interface LoginMapper extends BaseMapper<SysUser> {

    SysUser queryUser(String userName );

    List<Map<String,Object>> getUserPower(String userName);
}
