package com.yanshen.entity;

import com.yanshen.utils.SumBigDecimal;

import java.math.BigDecimal;


public class CountSum {

    @SumBigDecimal
    private BigDecimal amount;
    @SumBigDecimal
    private BigDecimal age;

    @SumBigDecimal
    private Double tall;
    private String name;

    @SumBigDecimal
    private Integer size;

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Double getTall() {
        return tall;
    }

    public void setTall(Double tall) {
        this.tall = tall;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAge() {
        return age;
    }

    public void setAge(BigDecimal age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
