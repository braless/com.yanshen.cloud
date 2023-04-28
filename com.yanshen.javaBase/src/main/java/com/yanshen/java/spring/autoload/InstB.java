package com.yanshen.java.spring.autoload;

import org.springframework.stereotype.Component;

@Component
public class InstB {

    public InstB() {
        System.out.println("B was Instanced");
    }
}
