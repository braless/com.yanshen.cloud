package com.yanshen.module;


import lombok.Data;

import javax.persistence.Column;

/**
 * @Auther: cyc
 * @Date: 2023/3/7 20:34
 * @Description:
 */
@Data
public class WeiboModel {

    private String id;
    private String name;
    private String url;
    private String phone;
    @Column(name = "id_card")
    private String idCard;

    @Override
    public String toString() {
        return "WeiBoUrl{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", phone='" + phone + '\'' +
                ", idCard='" + idCard + '\'' +
                '}';
    }
}
