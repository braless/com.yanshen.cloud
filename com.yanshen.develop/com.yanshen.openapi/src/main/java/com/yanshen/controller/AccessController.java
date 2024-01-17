package com.yanshen.controller;

import com.alibaba.fastjson.JSONObject;
import com.yanshen.utils.MsgInfo;
import com.yanshen.exception.TipException;
import com.yanshen.service.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accesstoken")
public class AccessController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    JwtTokenService jwtTokenService;

    @RequestMapping("/r")
    public MsgInfo<String> getToken(@RequestParam(value = "appid") String appid, @RequestParam(value = "sign") String sign, @RequestParam("timeStamp") String timeStamp) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("appid", appid);
        jsonObject.put("sign", sign);
        jsonObject.put("timeStamp", timeStamp);
        String tokenkey = Long.valueOf(appid).toString();//令牌key
        String plaintext = appid + "" + timeStamp;
        Integer validtime = 24 * 60;//令牌有效时间 分钟数
        Integer overtime = 10;//新令牌生成后，旧令牌过度可时间 分钟数，默认10分钟
        jsonObject.put("appid", appid);
        jsonObject.put("plaintext", plaintext);
        jsonObject.put("sign", sign);
        jsonObject.put("tokenkey", tokenkey);
        jsonObject.put("validtime", validtime);
        jsonObject.put("overtime", overtime);
        //System.out.println(100/0);
        //先验证签名
        MsgInfo verifySign = jwtTokenService.initVerify(jsonObject);
        logger.info(verifySign.getMessage());
        if (verifySign.getCode().equals("100000")){
            throw new TipException(verifySign.getMessage());
        }
        if (verifySign.getData().toString().equals("false")) {
            throw new TipException("签名为无效签名！");
        }
        String token;
        MsgInfo<String> msgInfo = jwtTokenService.getToken(jsonObject);
        if (msgInfo.getCode().equals("000000")) {
            token = msgInfo.getData();
        } else {
            throw new TipException(msgInfo.getMessage());
        }
        msgInfo.setData(token);
        return msgInfo;
    }

}
