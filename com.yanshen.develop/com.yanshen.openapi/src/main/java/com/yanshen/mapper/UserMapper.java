package com.yanshen.mapper;

import com.yanshen.entity.User;
import org.apache.ibatis.annotations.*;
import java.util.List;

/**
 * @BelongsProject: Spring-Boot
 * @BelongsPackage: com.yanshen.mapper
 * @Author: cuiyanchao
 * @CreateTime: 2019-06-05 11:34
 * @Description: ${Description}
 */
@Mapper
public interface UserMapper {

    User Sel(int id);

    User login(User user);

    int register(User user);

    List getGoodsList();
}
