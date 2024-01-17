package com.yanshen.java.DynamicProxy.example3;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Proxy;
import java.util.List;

public class NewProxy implements InvocationHandler {

    private Object target;

    public NewProxy() {

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("开始-------------------------");
        Object o =method.invoke(target,args);

        if (o instanceof Object){
            List<String> list = (List<String>) o;
            if (list.contains("AAA")){
                System.out.println("我tm给你删咯");
            }else {
                System.out.println("不包含此元素");
            }
        }
        System.out.println("结束++++++++++++++++++++++++++");
        return o;
    }

    public Object getInstance(Object target){
        this.target=target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),this);
    }
}
