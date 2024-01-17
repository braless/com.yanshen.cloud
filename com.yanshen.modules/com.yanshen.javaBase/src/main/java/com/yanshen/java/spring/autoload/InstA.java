package com.yanshen.java.spring.autoload;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("InstA")
public class InstA {
    @Autowired
    private InstB instB;

    public InstA() {
        System.out.println("A was Instanced");
    }

    public InstA(String age){
        System.out.println("InstA的有参构造方法"+age);
    }

    public void setInstB(InstB instB) {
        this.instB = instB;
    }

    public InstB getInstB() {
        return instB;
    }

    @Override
    public String toString() {
        return "InstA{" +
                "instB=" + instB +
                '}';
    }
}
