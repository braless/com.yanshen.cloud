package com.yanshen.orderservice.dev.excuteOrder;

import com.yanshen.orderservice.dev.excuteOrder.A;

/**
 * @Auther: cyc
 * @Date: 2023/3/30 16:21
 * @Description:
 */
public class B extends A {

    static {
        System.out.println("b");
    }
    {
        System.out.println("子类的构造代码块");
    }
    public B(){
        System.out.println("B");
    }
}
