package com.yanshen.java.controller;

public class Zhang extends Person{


    @Override
    public String add(String a, String b) {

        return a+b;
    }

    public static void main(String[] args) {
        Zhang zhang =new Zhang();
        zhang.add("3","5");
        System.out.println(zhang.toString());
    }

    @Override
    public String toString() {
        return "Zhang{" +
                "a='" + a + '\'' +
                ", b='" + b + '\'' +
                '}';
    }
}
