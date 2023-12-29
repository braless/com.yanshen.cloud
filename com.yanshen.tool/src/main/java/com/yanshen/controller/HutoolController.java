package com.yanshen.controller;


import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yanshen.base.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Slf4j
public class HutoolController {

    @RequestMapping("/do")
    public Result doSomething(){
        doGet();
        doPost_Body();
        return Result.success();
    }

    /**
     * @RequestBody修饰json对象参数
     * 参数只能使用JSONObject格式的，不能使用Map之类的，否则格式不对无法调用接口。
     */
    public void doPost_Body(){
        String url ="http://127.0.0.1:8080/isp-boot/push/sign";
        JSONObject object =JSONUtil.createObj();
        object.put("entname","陕西税务局");
        object.put("entcode","933301236890A");
        object.put("youxiaodate","2024/04/25");
        object.put("goodbehavior","（区县级）县级及以上的政府部门及有关部门依法对企业在从事工程建设活动中不良市场行为作出的警告、通报批评信息");
        object.put("appid","A2001");
        object.put("tsdate","2023/03/23");
        String post = HttpUtil.post(url, object.toString());

        //自定义post请求 包含设置header
        Map<String,String> headers=new HashMap<>();
        headers.put("name","zhangsan");
        HttpRequest.post("http://baidu.com").body(object.toString()).header("name","zhangsan").execute().body();
        log.info("doPost_Body: {}", JSONUtil.toJsonStr(post));


    }

    /**
     * 使用的参数map或者jsonObject都可以成功调用
     */
    public void doPost_Param(){
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("city", "北京");
        String result= HttpUtil.post("https://www.baidu.com", paramMap);

//文件上传只需将参数中的键指定（默认file），值设为文件对象即可，对于使用者来说，文件上传与普通表单提交并无区别
//        paramMap.put("file", FileUtil.file("D:\\face.jpg"));
//        String result2= HttpUtil.post("https://www.baidu.com", paramMap);
    }
    public void doGet(){
        String url = "http://www.sogou.com";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("query", 10086);
        // 无参GET请求
        String result = HttpUtil.get(url);
        // 带参GET请求
        String result2 = HttpUtil.get(url, paramMap);
        HttpRequest.get("http://baidu.com").body(paramMap.toString()).header("name","zhangsan").execute().body();
        log.info("doGet响应信息:{}",result);

    }
}
