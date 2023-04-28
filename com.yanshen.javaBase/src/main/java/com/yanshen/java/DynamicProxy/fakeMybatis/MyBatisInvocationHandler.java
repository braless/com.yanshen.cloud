package com.yanshen.java.DynamicProxy.fakeMybatis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyBatisInvocationHandler implements InvocationHandler {


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        Insert annotation = method.getAnnotation(Insert.class);

        String sql =annotation.value();

        System.out.println("z执行sql : " +sql);

        return null;
    }
}
