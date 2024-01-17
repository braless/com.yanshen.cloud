package com.yanshen.java.controller;

import lombok.Data;

@Data
public class Animal {
    public String name;
    public String age;

    public void dogsay(){
        System.out.println("hello");
    }
    public void dogwalk(){
        System.out.println("walk");
    }
    public void dogeat(){
        System.out.println("eat");
    }
}
