package com.yanshen.common;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-19 09:41
 * @Description:
 * @Location: com.yanshen.common
 * @Project: com.yanshen.cloud
 */
public enum ClaimEnum {

    WEB_COMMON("WEB_COMMON","公共信息"),
    WEB_USER("WEB_USER","用户信息"),
    WEB_ADMIN("WEB_ADMIN","管理员信息");

    private String code;
    private String value;


    ClaimEnum(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
