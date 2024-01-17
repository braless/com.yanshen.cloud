package com.yanshen.dto;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @description 人员信息实体类
 * @author lxj
 * @date 2022-04-28
 *
 */

@Entity
@Data
@Table(name="sys_organ_person")
public class SysOrganPerson implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Long personId;

    @Column(name = "person_name",nullable = false)
    @NotBlank
    private String personName;

    @Column(name = "person_nickname")
    private String personNickname;

    @Column(name = "organ_id")
    private Long organId;

    @Column(name = "id_number",nullable = false)
    @NotBlank
    private String idNumber;

    @Column(name = "phone",unique = true,nullable = false)
    @NotBlank
    private String phone;

    @Column(name = "wx")
    private String wx;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "sex",nullable = false)
    @NotBlank
    private String sex;

    @Column(name = "nation_id")
    private Long nationId;

    @Column(name = "education")
    private Long education;

    @Column(name = "school_name")
    private String schoolName;

    @Column(name = "birth_date")
    private String birthDate;

    @Column(name = "age")
    private Integer age;

    @Column(name = "profession")
    private String  profession;

    @Column(name = "organ_scope",nullable = false)
    @NotBlank
    private String organScope;

    @Column(name = "password",nullable = false)
    @NotBlank
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private Integer status;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_time",nullable = false)
    @NotNull
    private String createTime;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_time",nullable = false)
    @NotNull
    private String updateTime;

    public void copy(SysOrganPerson source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
