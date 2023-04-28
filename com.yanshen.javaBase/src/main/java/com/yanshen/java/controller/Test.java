package com.yanshen.java.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Test {


    public static void main(String[] args) {

        Map<String, Integer> integerMap = new HashMap();
        Map<String, Integer> integerMap1 = new HashMap<>();

        integerMap.put("abc", 4);
        integerMap1.put("4", 1);

        System.out.println(integerMap.hashCode());
        System.out.println(integerMap1.hashCode());

    }
}
