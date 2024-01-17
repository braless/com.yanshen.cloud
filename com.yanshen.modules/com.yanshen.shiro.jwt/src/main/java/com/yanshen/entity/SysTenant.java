package com.yanshen.entity;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;


/**
 * @Package: entity
 * @Author cyc
 * @CreateDate 2023-09-19
 * @describe 实体类
 */
@Data
@TableName("sys_tenant")
@ApiModel(value="SysTenant对象", description="")
    public class SysTenant implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
    * 
    */
    @ApiModelProperty(value = "")
    //@TableId(value = "id", type = IdType.AUTO)
    private Long id;


    /**
    * 
    */
    @ApiModelProperty(value = "")
    private String userName;


    /**
    * 
    */
    @ApiModelProperty(value = "")
    private String password;


    /**
    * 
    */
    @ApiModelProperty(value = "")
    private String nickName;


    /**
    * 
    */
    @ApiModelProperty(value = "")
    private String gender;


    /**
    * 
    */
    @ApiModelProperty(value = "")
    private String userAvatar;

    /**
     * 用户状态 0 正常 1 禁用 2 注销
     */
    @TableField
    private int status;


    /**
    * 
    */
    @ApiModelProperty(value = "")
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


}