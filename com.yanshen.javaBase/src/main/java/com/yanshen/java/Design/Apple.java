package com.yanshen.java.Design;

import com.yanshen.java.Design.Phone;

/**
 * <h3>spring-cloud</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2022-03-17 16:52
 **/
public class Apple implements Phone {

    public Apple(){
        this.make();
    }
    @Override
    public void make() {
        System.out.println("make apple phone!");
    }
}
