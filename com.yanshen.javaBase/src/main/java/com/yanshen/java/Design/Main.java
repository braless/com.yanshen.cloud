package com.yanshen.java.Design;

import com.yanshen.java.Design.PhoneFactory;

/**
 * <h3>spring-cloud</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2022-03-17 16:55
 **/
public class Main {

    public static void main(String[] args) {
        PhoneFactory factory = new PhoneFactory();
        Phone mi =factory.makePhone("MiPhone");
        Phone apple = factory.makePhone("iPhone");

    }
}
