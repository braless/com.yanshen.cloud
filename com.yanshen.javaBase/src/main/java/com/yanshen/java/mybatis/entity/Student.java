package com.yanshen.java.mybatis.entity;

import lombok.Data;

import java.util.Date;

/**
 * <h3>learn-spring-cloud</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2021-08-11 11:39
 **/

@Data
public class Student {
    private Integer studId;
    private String name;
    private String email;
    private Date dob;
    public Student() {

    }

    public Student(Integer studId) {
        this.studId = studId;
    }

    public Student(Integer studId, String name, String email, Date dob) {
        this.studId = studId;
        this.name = name;
        this.email = email;
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "Student [studId=" + studId + ", name=" + name + ", email="
                + email + ", dob=" + dob + "]";
    }
    // todo getter and setter

}