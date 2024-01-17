package com.yanshen.oauth2;

import com.alibaba.fastjson.JSON;
import com.yanshen.base.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
@Slf4j
public class AuthService {


    @Autowired
    private TokenEndpoint tokenEndpoint;

    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;


    /**
     * @param request
     * @return
     * @throws HttpRequestMethodNotSupportedException
     */
    public Result<Oauth2TokenDto> postAccessToken(HttpServletRequest request, Map<String, String> parameters) throws Exception {
        try {
            OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(request.getUserPrincipal(), parameters).getBody();
            Oauth2TokenDto oauth2TokenDto = Oauth2TokenDto.builder()
                    .token(oAuth2AccessToken.getValue())
                    .expiresIn(oAuth2AccessToken.getExpiresIn())
                    .tokenHead("Bearer ").build();
            if(oAuth2AccessToken.getRefreshToken() != null &&
                    StringUtils.isNotEmpty(oAuth2AccessToken.getRefreshToken().getValue())){
                oauth2TokenDto.setRefreshToken(oAuth2AccessToken.getRefreshToken().getValue());
            }

            if(StringUtils.isNotEmpty(parameters.get("username")) && StringUtils.isNotEmpty(parameters.get("password"))){
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(parameters.get("username"), parameters.get("password"));
                Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("user->> {}", JSON.toJSONString(authentication.getPrincipal()));
            }
            return Result.success(oauth2TokenDto);
        } catch (Exception e) {
            log.error("授权失败：{}", e.getMessage(), e);
            throw new Exception(e.getMessage());
        }
    }
}
