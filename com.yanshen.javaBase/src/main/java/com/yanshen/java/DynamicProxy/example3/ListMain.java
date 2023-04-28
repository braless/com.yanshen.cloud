package com.yanshen.java.DynamicProxy.example3;

import java.util.ArrayList;
import java.util.List;

public class ListMain {

    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");

        List list = new ArrayList();
        List listProxy = (List) new NewProxy().getInstance(list);
        listProxy.add("AAA");
        listProxy.add("BBB");

        System.out.println(listProxy.toString());


    }
}
