package com.yanshen.entity;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;


/**
 * @Package: entity
 * @Author cyc
 * @CreateDate 2023-09-21
 * @describe 用户角色表实体类
 */
@Data
@TableName("sys_user_role")
@ApiModel(value="SysUserRole对象", description="用户角色表")
    public class SysUserRole implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
    * 主键id
    */
    @ApiModelProperty(value = "主键id")
    private String id;


    /**
    * 用户id
    */
    @ApiModelProperty(value = "用户id")
    private String userId;


    /**
    * 角色id
    */
    @ApiModelProperty(value = "角色id")
    private String roleId;


}