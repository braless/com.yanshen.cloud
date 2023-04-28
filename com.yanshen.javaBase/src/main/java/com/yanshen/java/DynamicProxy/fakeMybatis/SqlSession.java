package com.yanshen.java.DynamicProxy.fakeMybatis;

import java.lang.reflect.Proxy;

public class SqlSession {

    public static UserMapper getMapper(Class clazz) {


        MyBatisInvocationHandler invocationHandler = new MyBatisInvocationHandler();
        return (UserMapper) Proxy.newProxyInstance(invocationHandler.getClass().getClassLoader(), new Class[]{clazz}, invocationHandler);

    }
}
