package com.yanshen.java.DynamicProxy.example1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * <h3>spring-cloud</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2022-03-22 11:46
 **/
public class MyInvocationHandler implements InvocationHandler {


    /** 目标对象 */
    private Object target;

    public MyInvocationHandler(Object target){
        this.target = target;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("------插入前置通知代码-------------");
        // 执行相应的目标方法
        Object rs = method.invoke(target,args);
        System.out.println("------插入后置处理代码-------------");
        return rs;

    }
}
