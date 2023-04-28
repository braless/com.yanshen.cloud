package com.yanshen.java.DynamicProxy.example2;

public class WangMeiLi implements Girl{
    @Override
    public void date() {
        System.out.println("你怎么现在才来");
    }

    @Override
    public void watchMovie() {
        System.out.println("这个电影不好看阿,好恐怖");
    }
}
