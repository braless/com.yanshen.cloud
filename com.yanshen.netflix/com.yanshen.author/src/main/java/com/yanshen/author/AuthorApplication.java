package com.yanshen.author;

import com.alipay.sofa.rpc.config.JAXRSProviderManager;
import com.fasterxml.jackson.core.filter.TokenFilter;
import com.yanshen.author.config.DeviceExceptionMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthorApplication {

    public static void main(String[] args) {
        JAXRSProviderManager.registerInternalProviderClass(TokenFilter.class);
        JAXRSProviderManager.registerInternalProviderClass(DeviceExceptionMapper.class);
        SpringApplication.run(AuthorApplication.class, args);
    }

}
