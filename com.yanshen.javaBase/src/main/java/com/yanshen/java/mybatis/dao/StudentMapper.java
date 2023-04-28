package com.yanshen.java.mybatis.dao;


import com.yanshen.java.mybatis.entity.Student;

/**
 * <h3>learn-spring-cloud</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2021-08-11 11:40
 **/
public interface StudentMapper {
    Student findStudentById(Integer id);
}
