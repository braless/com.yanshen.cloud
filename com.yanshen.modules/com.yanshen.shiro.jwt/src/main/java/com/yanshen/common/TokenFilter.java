package com.yanshen.common;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import com.yanshen.constants.AuthConstats;
import com.yanshen.util.JwtUtil;
import com.yanshen.util.ThreadLocalUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-18 17:25
 * @Description:
 * @Location: com.yanshen.common
 * @Project: com.yanshen.cloud
 */
public class TokenFilter implements Filter {


    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
       // Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ThreadLocalUtils.clearPlatform();
        HttpServletRequest req = (HttpServletRequest)request;
        String token = req.getHeader(AuthConstats.JWT_TOKEN_HEADER);
        if (token!=null){
            String userId = jwtUtil.getUserId(token);
            ThreadLocalUtils.setCurrentUserId(userId);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
