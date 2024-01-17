package com.yanshen.entity;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-12 17:49
 * @Description:
 * @Location: com.yanshen.entity
 * @Project: com.yanshen.cloud
 */
@Data
public class SysUser {
    private Integer userId;
    @NotEmpty(message = "用户名不能为空")
    private String userName;
    @NotEmpty(message = "密码不能为空")
    private String password;
    private String userRemarks;
    private Integer status;
    private Date createTime;
    private String salt;
}