package com.yanshen.entity;

/**
 * @BelongsProject: Spring-Boot
 * @BelongsPackage: com.yanshen.entity
 * @Author: cuiyanchao
 * @CreateTime: 2019-06-06 11:42
 * @Description: ${Description}
 */
public class Goods {
    private String id;
    private String goodsname;
    private String goodsnum;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname;
    }

    public String getGoodsnum() {
        return goodsnum;
    }

    public void setGoodsnum(String goodsnum) {
        this.goodsnum = goodsnum;
    }
}
