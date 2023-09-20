package com.yanshen.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 知否技术
 * @since 2022-03-09
 */
@Getter
@Setter
@Accessors(chain = true)
@TableName("t_user")
public class SysUser implements Serializable {

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
     * 性别：0-女 1-男
     */
    private Integer gender;

    /**
     * 账号
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createdDate;

    /**
     * 修改时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date updatedDate;

    /**
     * 删除标识
     */
    @TableLogic
    private Integer isDeleted;


}