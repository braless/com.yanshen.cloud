package com.yanshen.entity;

import com.yanshen.annotaition.ExcelField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h3>Braless</h3>
 * <p>测试导出类</p>
 *
 * @author : YanChao
 * @date : 2021-07-29 14:38
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Export {

    /**
     * 内置编码
     */
    private String nzbm;

    /**
     * 区域名称
     */
    @ExcelField(title = "区名", order = 1)
    private String qymc;
    /**
     * 镇数
     */
    @ExcelField(title = "姓名", order = 2, dataType = "string")
    private String name;

    /**
     * 村域面积
     */
    @ExcelField(title = "年龄", order = 4, dataType = "float")
    private Double age;
    /**
     * 村民小组
     */
    @ExcelField(title = "性别", order = 5, dataType = "string")
    private String gender;
    /**
     * 户数
     */
    @ExcelField(title = "身高", order = 6, dataType = "string")
    private Integer tall;

}
