package com.yanshen.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h3>Braless</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2021-07-29 14:28
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExcelFieldDTO implements Comparable<ExcelFieldDTO> {

    // excel的标题名称
    private String title;

    // 属性的父级标题名称（用于合并父级单元格）
    private String parentTitle;

    // 字段名称
    private String fieldName;

    // 每一个标题的顺序
    private Integer order;

    // 列宽度
    private Integer width;

    // 批注
    private String postil;

    // 是否有必填小红心
    private Boolean isRequired;

    // 数据类型（字符串-string, 整数-integer, 小数-float）
    private String dataType;

    // 是否为自增序号
    private Boolean isXh;

    @Override
    public int compareTo(ExcelFieldDTO o) {
        return this.order - o.order;
    }

}
