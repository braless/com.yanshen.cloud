package com.yanshen.entity;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiModel;
import lombok.Data;


/**
 * @Package: entity
 * @Author cyc
 * @CreateDate 2023-09-21
 * @describe 菜单权限表实体类
 */
@Data
@TableName("sys_permission")
@ApiModel(value="SysPermission对象", description="菜单权限表")
    public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
    * 主键id
    */
    @ApiModelProperty(value = "主键id")
    private String id;


    /**
    * 父id
    */
    @ApiModelProperty(value = "父id")
    private String parentId;


    /**
    * 菜单标题
    */
    @ApiModelProperty(value = "菜单标题")
    private String name;


    /**
    * 路径
    */
    @ApiModelProperty(value = "路径")
    private String url;


    /**
    * 组件
    */
    @ApiModelProperty(value = "组件")
    private String component;


    /**
    * 组件名字
    */
    @ApiModelProperty(value = "组件名字")
    private String componentName;


    /**
    * 一级菜单跳转地址
    */
    @ApiModelProperty(value = "一级菜单跳转地址")
    private String redirect;


    /**
    * 菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)
    */
    @ApiModelProperty(value = "菜单类型(0:一级菜单; 1:子菜单:2:按钮权限)")
    private Integer menuType;


    /**
    * 菜单权限编码
    */
    @ApiModelProperty(value = "菜单权限编码")
    private String perms;


    /**
    * 权限策略1显示2禁用
    */
    @ApiModelProperty(value = "权限策略1显示2禁用")
    private String permsType;


    /**
    * 菜单排序
    */
    @ApiModelProperty(value = "菜单排序")
    private Double sortNo;


    /**
    * 聚合子路由: 1是0否
    */
    @ApiModelProperty(value = "聚合子路由: 1是0否")
    private Boolean alwaysShow;


    /**
    * 菜单图标
    */
    @ApiModelProperty(value = "菜单图标")
    private String icon;


    /**
    * 是否路由菜单: 0:不是  1:是（默认值1）
    */
    @ApiModelProperty(value = "是否路由菜单: 0:不是  1:是（默认值1）")
    private Boolean isRoute;


    /**
    * 是否叶子节点:    1:是   0:不是
    */
    @ApiModelProperty(value = "是否叶子节点:    1:是   0:不是")
    private Boolean isLeaf;


    /**
    * 是否缓存该页面:    1:是   0:不是
    */
    @ApiModelProperty(value = "是否缓存该页面:    1:是   0:不是")
    private Boolean keepAlive;


    /**
    * 是否隐藏路由: 0否,1是
    */
    @ApiModelProperty(value = "是否隐藏路由: 0否,1是")
    private Integer hidden;


    /**
    * 描述
    */
    @ApiModelProperty(value = "描述")
    private String description;


    /**
    * 创建人
    */
    @ApiModelProperty(value = "创建人")
    private String createBy;


    /**
    * 创建时间
    */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;


    /**
    * 更新人
    */
    @ApiModelProperty(value = "更新人")
    private String updateBy;


    /**
    * 更新时间
    */
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


    /**
    * 删除状态 0正常 1已删除
    */
    @ApiModelProperty(value = "删除状态 0正常 1已删除")
    private Integer delFlag;


    /**
    * 是否添加数据权限1是0否
    */
    @ApiModelProperty(value = "是否添加数据权限1是0否")
    private Integer ruleFlag;


    /**
    * 按钮权限状态(0无效1有效)
    */
    @ApiModelProperty(value = "按钮权限状态(0无效1有效)")
    private String status;


    /**
    * 外链菜单打开方式 0/内部打开 1/外部打开
    */
    @ApiModelProperty(value = "外链菜单打开方式 0/内部打开 1/外部打开")
    private Boolean internalOrExternal;


}