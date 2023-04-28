package com.yanshen.java.mybatis.utils;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * <h3>learn-spring-cloud</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2021-08-11 11:42
 **/
public class MyBatisSqlSessionFactory {
    private static SqlSessionFactory sqlSessionFactory;

    private static final Properties PROPERTIES = new Properties();
//
//    static {
//        try {
//            InputStream is = DataSourceFactory.class.getResourceAsStream("/application.yml");
//            PROPERTIES.load(is);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    public static SqlSessionFactory getSqlSessionFactory() {
        if (sqlSessionFactory == null) {
            try (InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml")) {
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                throw new RuntimeException(e.getCause());
            }
        }
        return sqlSessionFactory;
    }

    public static SqlSession getSqlSession() {
        SqlSessionFactory sqlSessionFactory =getSqlSessionFactory();
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession;
    }

    public static Connection getConnection() {
        String driver = PROPERTIES.getProperty("jdbc.driverClassName");
        String url = PROPERTIES.getProperty("jdbc.url");
        String username = PROPERTIES.getProperty("jdbc.username");
        String password = PROPERTIES.getProperty("jdbc.password");
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}