package com.yanshen.dev.service;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier(value = "impl2")
public class ZhuRuService2 implements ZhuRuService {
    @Override
    public String getName() {
        return "IServiceImpl2";
    }
}
