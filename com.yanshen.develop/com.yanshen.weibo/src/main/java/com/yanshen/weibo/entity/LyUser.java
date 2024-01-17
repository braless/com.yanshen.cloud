package com.yanshen.weibo.entity;

import lombok.Data;

/**
 * <h3>Braless</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2021-04-29 09:29
 **/
@Data
public class LyUser {

    public Long id;
    public String idcard;
    private String address;
    private String age;
    private String name;

}
