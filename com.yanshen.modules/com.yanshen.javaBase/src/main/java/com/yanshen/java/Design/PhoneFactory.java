package com.yanshen.java.Design;

import com.yanshen.java.Design.Phone;

/**
 * <h3>spring-cloud</h3>
 * <p></p>
 *  工厂模式
 * @author : YanChao
 * @date : 2022-03-17 16:51
 **/
public class PhoneFactory {

    public Phone makePhone(String phoneType) {
        if(phoneType.equalsIgnoreCase("MiPhone")){
            return new XiaoMi();
        }
        else if(phoneType.equalsIgnoreCase("iPhone")) {
            return new Apple();
        }
        return null;
    }
}
