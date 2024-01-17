package com.yanshen.config;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.yanshen.base.Result;
import com.yanshen.base.ResultCode;
import com.yanshen.constant.AuthConstant;
import com.yanshen.exception.CustomException;
import com.yanshen.oauth2.service.UserCacheClient;
import com.yanshen.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 鉴权管理器
 */
@Slf4j
@Component
public class AuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private IgnoreWhiteProperties ignoreWhiteProperties;

    @Autowired
    private SecurityProperties securityProperties;

    @Lazy
    @Autowired
    private UserCacheClient userCacheClient;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> mono, AuthorizationContext authorizationContext) {
//        if(true){
//            return Mono.just(new AuthorizationDecision(true));
//        }
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        //跨域的预检请求直接放行
        if (request.getMethod() == HttpMethod.OPTIONS) {
            return Mono.just(new AuthorizationDecision(true));
        }

        String url = request.getURI().getPath();
        String method = request.getMethodValue();
        PathMatcher pathMatcher = new AntPathMatcher();
        //白名单路径直接放行
        List<String> ignoreUrls = ignoreWhiteProperties.getWhites();
        for (String ignoreUrl : ignoreUrls) {
            if (pathMatcher.match(ignoreUrl, url)) {
                return Mono.just(new AuthorizationDecision(true));
            }
        }

        String token = getToken(request);
        if (StrUtil.isBlank(token)) {
            return Mono.just(new AuthorizationDecision(false));
        }

//        //不同用户体系登录不允许互相访问
//        try {
//            String token = request.getHeaders().getFirst(securityProperties.getHeader());
//            if (StrUtil.isEmpty(token)) {
//                return Mono.just(new AuthorizationDecision(false));
//            }
//            String realToken = token.replace(securityProperties.getTokenStartWith(), "");
//            JWSObject jwsObject = JWSObject.parse(realToken);
//            String userStr = jwsObject.getPayload().toString();
//            OnlineUserDto userDto = JSONUtil.toBean(userStr, OnlineUserDto.class);
//            if ("AuthConstant.ADMIN_CLIENT_ID".equals(userDto.getClientId()) && !pathMatcher.match("AuthConstant.ADMIN_URL_PATTERN", uri.getPath())) {
//                return Mono.just(new AuthorizationDecision(false));
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return Mono.just(new AuthorizationDecision(false));
//        }

        //校验权限
        Map<Object, Object> resourceRolesMap = redisUtils.hmget(AuthConstant.SYS_ROLE_MENU_KEY);
        if(CollectionUtils.isEmpty(resourceRolesMap)){
            Result result = userCacheClient.initSysUserResource();
            if(result.getCode() != ResultCode.SUCCESS.getCode()){
                throw new CustomException(result.getMsg());
            }
            resourceRolesMap = (Map<Object, Object>) result.getData();
        }

        Iterator<Object> iterator = resourceRolesMap.keySet().iterator();
        List<String> authorities = new ArrayList<>();
        String matchUrl = url;
        if(url.contains("api") || url.contains("personnel")){
            matchUrl = url.substring(url.indexOf("/", 2));
        }
        while (iterator.hasNext()) {
            String pattern = (String) iterator.next();
            String matchPattern = pattern.substring(pattern.indexOf("/", 2));
            if(matchPattern.contains("_&&_") &&
                    Objects.equals(matchPattern.split("_&&_")[0], matchUrl)
                    && Objects.equals(matchPattern.split("_&&_")[1], method.toLowerCase(Locale.ROOT))) {
                matchUrl = matchUrl + "_&&_" + method.toLowerCase(Locale.ROOT);
            }
            if (pathMatcher.match(matchPattern, matchUrl)) {
                authorities.addAll(Convert.toList(String.class, resourceRolesMap.get(pattern)).stream().map(item -> item.split("_&_")[0]).collect(Collectors.toList()));
            }

        }
        authorities = authorities.stream().map(i -> i = "ROLE_" + i).collect(Collectors.toList());
        //认证通过且角色匹配的用户可访问当前路径
        List<String> finalAuthorities = authorities;
        log.info("=====>>> {}", finalAuthorities);

        Mono<AuthorizationDecision> authorizationDecisionMono = mono
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(roleId -> {
                    log.info("访问路径：{}", url);
                    log.info("用户角色roleId：{}", roleId);
                    log.info("资源需要权限authorities：{}", finalAuthorities);
                    if(roleId.equals("ROLE_admin")){
                        return true;
                    }
                    return finalAuthorities.contains(roleId);
                })
//                .any(authorities::contains)
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
        return authorizationDecisionMono;
    }

    private String getToken(ServerHttpRequest request) {
        String token = request.getHeaders().getFirst(securityProperties.getHeader());
        // 如果前端设置了令牌前缀，则裁剪掉前缀
        if (StringUtils.isNotEmpty(token) && token.startsWith(securityProperties.getTokenStartWith())) {
            token = token.replaceFirst(securityProperties.getTokenStartWith(), StringUtils.EMPTY);
        } else {
            log.info("非法Token：{}", token);
        }
        return token;
    }

}