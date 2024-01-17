package com.yanshen.java.DynamicProxy.fakeMybatis;

public class Main {

    public static void main(String[] args) {
        UserMapper mapper = SqlSession.getMapper(UserMapper.class);
        mapper.insert();
    }
}
