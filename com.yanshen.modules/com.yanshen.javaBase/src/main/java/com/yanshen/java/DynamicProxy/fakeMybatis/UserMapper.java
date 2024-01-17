package com.yanshen.java.DynamicProxy.fakeMybatis;

public interface UserMapper {

    @Insert("insert into table ")
    void insert();
}
