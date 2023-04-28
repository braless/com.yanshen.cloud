package com.yanshen.weibo.service;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * <h3>cyc_spring</h3>
 * <p>归属地查询</p>
 *
 * @author : YanChao
 * @date : 2020-09-04 10:43
 **/
@Service
public class TelAreaService {


    RestTemplate template = new RestTemplate();

    private String url = "http://tcc.taobao.com/cc/json/mobile_tel_segment.htm";

    private String ipurl = "http://ip-api.com/json/";

    private String mporeip = "http://whois.pconline.com.cn/ipJson.jsp?";

    /**
     * 获取手机号码归属地
     *
     * @param tel
     * @return
     */
    public String postTest(String tel) {
        //表单形式提交请求体
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("tel", tel);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, getHeader());
        ResponseEntity<String> response = template.postForEntity(url, request, String.class);
        String result = response.getBody();
        //System.out.println(result);
        String[] a = result.split(":");
        // System.out.println(a[7]);
        String 归属地 = a[7].substring(0, 6).replace("'", "");
        return 归属地;
    }


    /**
     * 获取ip地址 不太准确
     *
     * @param ip
     * @return
     */
    public String getRequestIpinfo(String ip) {
        Map<String, String> params = new HashMap<>();
        params.put("lang", "zh-CN");
        ResponseEntity<String> response = template.postForEntity(ipurl + ip + "?lang=zh-CN", params, String.class);
        String result = response.getBody();
        JSONObject ipinfo = JSONObject.parseObject(result);
        String address = ipinfo.getString("regionName") + "-" + ipinfo.getString("city");
        return address;

    }

    public String getIpurl(String ip) {
        Map<String, String> params = new HashMap<>();
        ResponseEntity<String> response = template.postForEntity(mporeip + "ip=" + ip + "&json=true", params, String.class);
        String result = response.getBody();
        JSONObject ipinfo = JSONObject.parseObject(result);
        String address = ipinfo.getString("addr");
        return address;
    }

    /**
     * 生成header
     *
     * @return
     */
    private HttpHeaders getHeader() {
        HttpHeaders headers = new HttpHeaders();
        // headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

}
