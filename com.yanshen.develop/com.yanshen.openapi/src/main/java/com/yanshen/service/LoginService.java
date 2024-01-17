package com.yanshen.service;

import com.alibaba.fastjson.JSONObject;
import dto.TenantUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import util.MsgInfo;
import util.TokenInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * <h3>spring-cloud</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2022-02-25 17:25
 **/
@Service
public class LoginService {

    @Autowired
    TokenService jwtTokenService;
    /**
     * 登录 调用查询数据库
     *
     * @author songyuanming
     *
     */
    public MsgInfo<TenantUserDTO> platformLogin(TenantUserDTO tenantUserDTO) {
        MsgInfo<TenantUserDTO> msgInfo = new MsgInfo<>();
        boolean riskSend = true;
        Map<String,Object> riskResultMap = new HashMap<>();
        TenantUserDTO param = tenantUserDTO;
        TokenInfo 	tokenInfo = jwtTokenService.getToken(param);
        tenantUserDTO.setToken(tokenInfo.getToken());
        msgInfo.setData(tenantUserDTO);
        return msgInfo;

    }
}
