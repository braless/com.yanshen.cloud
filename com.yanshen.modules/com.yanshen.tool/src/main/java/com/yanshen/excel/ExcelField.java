package com.yanshen.excel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <h3>Braless</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2021-07-29 14:30
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelField {

    /**
     * 属性的标题名称
     */
    String title();

    /**
     * 属性的父级标题名称（用于合并父级单元格）,必须与order联合一起使用
     */
    String parentTitle() default "";

    /**
     * 在excel的顺序
     */
    int order() default Integer.MAX_VALUE;

    /**
     * 字符数
     */
    int width() default 3;

    /**
     * 属性的批注
     */
    String postil() default "";

    /**
     * 是否必填（添加小红心）
     */
    boolean isRequired() default false;

    /**
     * 数据类型（字符串-string, 整数-integer, 小数-float）
     */
    String dataType() default "string";
}
