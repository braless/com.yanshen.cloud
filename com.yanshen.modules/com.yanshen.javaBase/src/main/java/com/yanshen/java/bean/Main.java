package com.yanshen.java.bean;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-config-1.xml");
        //System.out.println(context.getBean("personBean"));
        Person person = (Person) context.getBean("personBean");
        System.out.println(person);
        //
        BeanFactory beanFactory;
        ((ClassPathXmlApplicationContext) context).destroy();
    }
}
