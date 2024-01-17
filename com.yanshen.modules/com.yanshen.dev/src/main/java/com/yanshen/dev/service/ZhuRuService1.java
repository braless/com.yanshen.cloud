package com.yanshen.dev.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Qualifier(value = "impl1")
public class ZhuRuService1 implements ZhuRuService {
    @Override
    public String getName() {
        return "IServiceImpl1";
    }
}
