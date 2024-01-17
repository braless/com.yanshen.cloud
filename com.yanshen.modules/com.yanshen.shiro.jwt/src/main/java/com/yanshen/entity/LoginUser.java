package com.yanshen.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-18 17:05
 * @Description:
 * @Location: com.yanshen.entity
 * @Project: com.yanshen.cloud
 */
@Getter
@Setter
public class LoginUser {

    private static final long serialVersionUID = 1L;

    /**
     * 基于雪花算法生成id
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 姓名
     */
    private String nickName;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 性别：0-女 1-男
     */
    private Integer sex;

    /**
     * 账号
     */
    private String userName;


    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 删除标识
     */
    @TableLogic
    private Integer isDeleted;

    /**
     * 登录时间
     */
    private String loginTime;
    private String expireAt;
    private String token;
    private String refreshToken;
}
