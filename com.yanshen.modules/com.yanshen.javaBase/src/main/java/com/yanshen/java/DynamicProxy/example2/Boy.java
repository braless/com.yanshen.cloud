package com.yanshen.java.DynamicProxy.example2;

public class Boy {


    public static void main(String[] args) {

        //隔壁有个女孩叫王美丽
        Girl girl =new WangMeiLi();

        //王美丽有个家庭 想要王美丽出来 需要经过她家庭的同意
        WangMeiLiProxy family =new WangMeiLiProxy(girl);

        //王美丽她妈 这个代理 我才能叫王美丽出来约会
        Girl mother = (Girl) family.getProxyInstance();

        mother.date();
        System.out.println("-----------------------");
        mother.watchMovie();
    }
}
