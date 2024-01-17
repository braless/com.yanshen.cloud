package com.yanshen.utils;

public class SercretKey {
    private static final long serialVersionUID = 1L;
    private Long appid;
    private String publickey;
    private String privatekey;
    private Integer keytype;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getAppid() {
        return appid;
    }

    public void setAppid(Long appid) {
        this.appid = appid;
    }

    public String getPublickey() {
        return publickey;
    }

    public void setPublickey(String publickey) {
        this.publickey = publickey;
    }

    public String getPrivatekey() {
        return privatekey;
    }

    public void setPrivatekey(String privatekey) {
        this.privatekey = privatekey;
    }

    public Integer getKeytype() {
        return keytype;
    }

    public void setKeytype(Integer keytype) {
        this.keytype = keytype;
    }
}
