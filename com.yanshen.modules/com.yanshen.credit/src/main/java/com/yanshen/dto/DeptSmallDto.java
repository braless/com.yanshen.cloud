package com.yanshen.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author luojc
 * @date 2019-6-10 16:32:18
 */
@Data
public class DeptSmallDto implements Serializable {

    private Long id;

    private String name;
}