package com.yanshen.author.service.Impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.yanshen.author.entity.ServiceModel;
import com.yanshen.author.service.AuthService;
import org.springframework.stereotype.Service;

/**
 * <h3>spring-cloud</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2022-03-01 16:27
 **/
@Service
@SofaService(interfaceType = AuthService.class, uniqueId = ServiceModel.ES, bindings = { @SofaServiceBinding(bindingType = "bolt"),@SofaServiceBinding(bindingType = "rest") })
public class AuthServiceImpl implements AuthService {
    @Override
    public String get() {
        return "this is ok";
    }
}
