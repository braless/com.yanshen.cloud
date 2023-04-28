package com.yanshen.java.controller;

public abstract class Person {
    public String a;
    public String b;
    public  String add(String a,String b){
        return a+b;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }
}
