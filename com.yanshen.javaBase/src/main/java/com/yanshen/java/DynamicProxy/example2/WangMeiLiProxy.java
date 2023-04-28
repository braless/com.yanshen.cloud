package com.yanshen.java.DynamicProxy.example2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class WangMeiLiProxy  implements InvocationHandler {

    private Girl girl;

    public WangMeiLiProxy(Girl girl){
        super();
        this.girl=girl;


    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        doBefore();
        Object o =method.invoke(girl,args);
        doAfter();
        return o;
    }


    public void doBefore(){
        System.out.println("她妈问:这男的有没有车有没有房子");
    }

    public void doAfter(){
        System.out.println("她爸问:看电影有没有动手动脚?");
    }

    public Object getProxyInstance(){
        return Proxy.newProxyInstance(girl.getClass().getClassLoader(),girl.getClass().getInterfaces(),this);
    }

}
