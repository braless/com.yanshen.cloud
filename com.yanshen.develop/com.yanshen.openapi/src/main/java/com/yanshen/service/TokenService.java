package com.yanshen.service;

import dto.AccountDTO;
import dto.TenantAccountDTO;
import dto.TenantUserDTO;
import entity.Account;
import org.springframework.stereotype.Service;
import util.TokenInfo;
import util.TokenUtil;

/**
 * <h3>spring-cloud</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2022-02-25 17:18
 **/
@Service
public class TokenService {


    
    public TokenInfo getToken(AccountDTO account) {
        String token = TokenUtil.createToken(account.getAccountid());
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setToken(token);
        return tokenInfo;
    }

    
    public Account getAccount(String token) {
        String accountId = TokenUtil.getAccountId(token);
        Account account = new Account();
        account.setAccountid(accountId);
        return account;
    }


    
    public TokenInfo getToken(TenantUserDTO tenantUserDTO) {
        String token = TokenUtil.createToken(tenantUserDTO);
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setToken(token);
        return tokenInfo;
    }

    
    public TokenInfo getToken(TenantAccountDTO tenantAccountDTO) {
        String token = TokenUtil.createToken(tenantAccountDTO);
        TokenInfo tokenInfo = new TokenInfo();
        tokenInfo.setToken(token);
        return tokenInfo;
    }
}
