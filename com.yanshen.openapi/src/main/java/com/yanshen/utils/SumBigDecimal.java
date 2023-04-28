package com.yanshen.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h3>releP-hw4分支（济南项目迭代六）</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2021-08-14 10:06
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.METHOD })
public @interface SumBigDecimal {
    boolean required() default true;
}
