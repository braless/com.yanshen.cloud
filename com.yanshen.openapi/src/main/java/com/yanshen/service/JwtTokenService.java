package com.yanshen.service;

import com.alibaba.fastjson.JSONObject;
import com.yanshen.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@Service
public class JwtTokenService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    RedisUtil redisUtil;

    private static  final  String OPEN_API="OPEN_API_";

    private static  final  String SIGNATURE_TOKEN="SIGNATURE_TOKEN";


    public MsgInfo initVerify(@RequestBody JSONObject jsonObject) {
        MsgInfo msgInfo = new MsgInfo<>();
        try {
            String plaintext = jsonObject.getString("plaintext");//数字签名所用字符串
            String sign = jsonObject.getString("sign");//数字签名
            Long appid = jsonObject.getLong("appid");//公钥关键字

            if (CommonUtil.isEmpty(plaintext)) {
                return errorMsg(msgInfo, CodeEnum.PARAMS_FAILTURE.getCode(), "签名所用字符串为空，请求失败！");
            }
            if (CommonUtil.isEmpty(sign)) {
                return errorMsg(msgInfo, CodeEnum.PARAMS_FAILTURE.getCode(), "数字签名为空，请求失败！");
            }
            if (null == appid) {
                return errorMsg(msgInfo, CodeEnum.PARAMS_FAILTURE.getCode(), "appid为空，请求失败！");
            }
            String publicKey = redisUtil.get(OPEN_API + appid);
            if (CommonUtil.isEmpty(publicKey)) {
                return errorMsg(msgInfo, CodeEnum.PARAMS_FAILTURE.getCode(), "密钥失效，请求重新获取密钥！");
            }
            //验证数据签名
            boolean flag = ECDSAUtil.initVerify(publicKey, plaintext, sign);
            msgInfo.setData(flag);
        } catch (Exception e) {
            logger.error("验证数字签名出错：", e);
            return errorMsg(msgInfo, CodeEnum.PARAMS_FAILTURE.getCode(), "数字签名无效！");
        }
        return msgInfo;
    }

    MsgInfo<String> errorMsg(MsgInfo<String> msgInfo, String errorCode, String errorInfo) {
        msgInfo.setCode(errorCode);
        msgInfo.setMessage(errorInfo);
        return msgInfo;
    }


    public MsgInfo getToken(JSONObject jsonObject){
        MsgInfo<String> msgInfo = new MsgInfo<>();

        try {
            Long appid = jsonObject.getLong("appid");
            String tokenkey = jsonObject.getString("tokenkey");//令牌key
            Integer validtime = jsonObject.getInteger("validtime");//令牌有效时间 分钟数
            Integer overtime = jsonObject.getInteger("overtime");//新令牌生成后，旧令牌过度可时间 分钟数，默认10分钟

            if(null ==appid){
                return errorMsg(msgInfo, CodeEnum.PARAMS_FAILTURE.getCode(), "appid传输错误！");
            }
            if(CommonUtil.isEmpty(tokenkey)){
                tokenkey = Long.valueOf(appid).toString();//令牌key，默认为appid
            }
            if(validtime==null) {
                return errorMsg(msgInfo, CodeEnum.PARAMS_FAILTURE.getCode(), "令牌有效时间传输错误！");
            }
            if(overtime==null) {
                overtime = 10;//旧令牌过度时间，默认10分钟
            }

            String publicKey = this.redisUtil.get(OPEN_API+appid);
            if(CommonUtil.isEmpty(publicKey)){
                return errorMsg(msgInfo, CodeEnum.PARAMS_FAILTURE.getCode(), "密钥失效，请求重新获取密钥！");
            }
            //生成token 放入redis中
            String token = UUID.randomUUID().toString().replaceAll("-", "");
            //将所有老的令牌失效时间改为10分钟
            //redisService.updateTimeByPattern(RedisConst.SIGNATURE_TOKEN+tokenkey, overtime);
            //将新的令牌放进缓存中
            redisUtil.set(SIGNATURE_TOKEN+tokenkey+":"+token, token,validtime);
            msgInfo.setData(token);
            logger.info("生成token成功!:{}",token);
        } catch (Exception e) {
            logger.error("获取令牌错误",e);
            return errorMsg(msgInfo, CodeEnum.PARAMS_FAILTURE.getCode(), "获取令牌错误，请检查参数");
        }
        return msgInfo;
    }
}
