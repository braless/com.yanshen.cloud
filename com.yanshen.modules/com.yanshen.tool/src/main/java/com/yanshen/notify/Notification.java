package com.yanshen.notify;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @description: 通知类
 * @author: Yanchao
 * @create: 2021-01-16 10:34
 **/
public class Notification implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        pushBark();
    }


    public void pushBark() {
        ResponseEntity<String> response = null;
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.day.app/4ppVFBUZxhEnxPzumvtsdF/服务状态/Bean服务已停止";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, new HttpEntity<String>(new HttpHeaders()), String.class);
        System.out.println(response);
    }
}
