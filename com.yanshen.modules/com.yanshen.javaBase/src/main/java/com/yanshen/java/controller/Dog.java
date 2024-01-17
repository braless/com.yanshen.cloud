package com.yanshen.java.controller;

public class Dog extends Animal {
    public void eat(){
        System.out.println("eat");
    }
    public static void main(String[] args) {
        Animal dog =new Pig();
        String a =new String("aaa");
        String b= new String("aaa");

        String c ="aaa";
        String d= "aaa";
        System.out.println(d.intern());
        System.out.println(c==d);
        System.out.println(a==b);
        System.out.println(a==c);;
    }
    static class Pig extends  Dog{

        public String pigSay(String a){
            System.out.println("pig say");
            return "A";
        }
        public String pigSay(){
            return "b";
        }

    }
}
