package com.yanshen.entity.dto;

import lombok.Data;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-21 14:30
 * @Description:
 * @Location: com.yanshen.entity.dto
 * @Project: com.yanshen.cloud
 */
@Data
public class LoginDTO {


    /**
     * 账号
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 验证码
     */
    private String captcha;

    /**
     * 验证码key
     */
    private String checkKey;
}
