package com.yanshen.java.SPI;

import java.util.List;

/**
 * <h3>spring-cloud</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2022-03-25 16:45
 **/
public interface Search {
    public List<String> searchDoc(String keyword);
}
