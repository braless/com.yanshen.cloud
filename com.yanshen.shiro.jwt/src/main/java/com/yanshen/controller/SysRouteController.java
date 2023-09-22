package com.yanshen.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yanshen.common.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-21 16:16
 * @Description:
 * @Location: com.yanshen.controller
 * @Project: com.yanshen.cloud
 */

@RestController
@RequestMapping("/sys-route")
public class SysRouteController {


    @RequestMapping("/list")
    public Result getAllRoute(){
        String routes="[\n" +
                "  {\n" +
                "    path: '/',\n" +
                "    name: 'Root',\n" +
                "    component: 'Layout',\n" +
                "    meta: {\n" +
                "      title: '首页',\n" +
                "      icon: 'home-2-line',\n" +
                "      breadcrumbHidden: true,\n" +
                "    },\n" +
                "    children: [\n" +
                "      {\n" +
                "        path: 'index',\n" +
                "        name: 'Index',\n" +
                "        component: '@/views/index',\n" +
                "        meta: {\n" +
                "          title: '首页',\n" +
                "          icon: 'home-2-line',\n" +
                "          noClosable: true,\n" +
                "        },\n" +
                "      },\n" +
                "    ],\n" +
                "  },\n" +
                "]";
        JSONObject obj =new JSONObject();
        obj.set("list",JSONUtil.parse(routes));
        return Result.success(obj);

    }
}
