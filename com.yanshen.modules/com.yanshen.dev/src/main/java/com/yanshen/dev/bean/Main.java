package com.yanshen.dev.bean;

public class Main {

    public static void main(String[] args) {
        try {
            User user=new User();

            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
