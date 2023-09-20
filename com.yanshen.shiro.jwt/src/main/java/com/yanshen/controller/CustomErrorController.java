package com.yanshen.controller;

import cn.hutool.json.JSONObject;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-19 16:03
 * @Description:
 * @Location: com.yanshen.controller
 * @Project: com.yanshen.cloud
 */
@RestController
public class CustomErrorController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(PATH)
    public ResponseEntity<?> handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                JSONObject error =new JSONObject();
                error.set("code",500);
                error.set("message","404,页面未找到");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
            }
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("其他错误");
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
