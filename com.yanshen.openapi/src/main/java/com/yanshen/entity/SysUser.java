package com.yanshen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Auther: @Yanchao
 * @Date: 2023-08-28 11:23
 * @Description:
 * @Location: com.yanshen.entity
 * @Project: com.yanshen.cloud
 */
@Data
@TableName("sys_user")
public class SysUser {

    private String id;
    private String userName;
    private String userAccount;
    private String password;
    private String salt;
    private String email;
    private String avator;
    private Integer status;
    private Integer delFlag;
    private Date createTime;

}
