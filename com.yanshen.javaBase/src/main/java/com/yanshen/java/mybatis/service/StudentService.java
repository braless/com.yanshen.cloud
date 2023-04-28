package com.yanshen.java.mybatis.service;

import com.yanshen.java.mybatis.dao.StudentMapper;
import com.yanshen.java.mybatis.entity.Student;
import com.yanshen.java.mybatis.utils.MyBatisSqlSessionFactory;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * <h3>learn-spring-cloud</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2021-08-11 11:40
 **/
@Service
public class StudentService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public Student findStudentById(Integer studId) {
        logger.debug("Select Student By ID :{}", studId);
        SqlSession sqlSession = MyBatisSqlSessionFactory.getSqlSession();
        try {
            StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
            Student studentById = studentMapper.findStudentById(studId);
            return studentById;
            //return sqlSession.selectOne("com.mybatis3.StudentMapper.findStudentById", studId);
        } finally {
            sqlSession.close();
        }
    }
}
