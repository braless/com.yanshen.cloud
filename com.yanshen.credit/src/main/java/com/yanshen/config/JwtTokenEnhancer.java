package com.yanshen.config;

import com.yanshen.dto.SecurityUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * JWT内容增强器
 */
@Slf4j
@Component
public class JwtTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        try {
            SecurityUser securityUser = (SecurityUser) authentication.getPrincipal();
            Map<String, Object> info = new HashMap<>();
            //把用户ID设置到JWT中
            info.put("id", securityUser.getId());
            info.put("client_id", securityUser.getClientId());
            info.put("userCode", securityUser.getUserCode());
            info.put("companyCode", securityUser.getCompanyCode());
            info.put("deptCode", securityUser.getDeptCode());
            info.put("scope", securityUser.getDataScopes());
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(info);
            return accessToken;
        } catch (Exception e) {
            log.warn("非用户类型：{}", e.getMessage());
        }
        return accessToken;
    }
}
