package com.yanshen.weibo.mapper;

import com.yanshen.weibo.entity.LyUser;

import java.util.List;

public interface UserMapper {

    List<LyUser> getUser();
    void update(LyUser user);
}
