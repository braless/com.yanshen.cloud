package com.yanshen.java.spring.autoload;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainClass {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =new AnnotationConfigApplicationContext(MainConfig.class);

        InstA instA = (InstA) context.getBean("InstA");
        System.out.println(instA);
    }


}
