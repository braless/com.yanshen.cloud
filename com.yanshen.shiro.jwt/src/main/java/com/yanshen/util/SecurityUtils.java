package com.yanshen.util;

import com.yanshen.entity.LoginUser;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-18 17:18
 * @Description:
 * @Location: com.yanshen.util
 * @Project: com.yanshen.cloud
 */
public class SecurityUtils {

    private static HttpServletRequest request;

    @Autowired
    public void setRequest(HttpServletRequest request) {
        SecurityUtils.request = request;
    }

    public static LoginUser getUser(){

        return null;
    }

}
