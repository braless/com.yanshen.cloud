package com.yanshen.mapper;

import com.yanshen.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 知否技术
 * @since 2022-03-09
 */
@Mapper
public interface UserMapper extends BaseMapper<SysUser> {

}
