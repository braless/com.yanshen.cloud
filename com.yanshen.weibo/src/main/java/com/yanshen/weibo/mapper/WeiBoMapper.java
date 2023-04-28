package com.yanshen.weibo.mapper;


import com.yanshen.weibo.entity.TenentQQ;
import com.yanshen.weibo.entity.Weibo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WeiBoMapper {
    Weibo find(Weibo weibo);

    int insert(Weibo weibo);

    List<TenentQQ> query(@Param("qq") String qq);
}
