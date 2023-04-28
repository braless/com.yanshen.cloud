package com.yanshen.controller;

import com.alibaba.fastjson.JSONObject;
import com.yanshen.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 *	 获取公钥 私钥（令牌）
 *	1、私钥用于签名
 *	2、公钥用于签名验证
 * <p>Title: KeyController</p>
 * <p>Description: </p>
 * @author zhanghc
 * @date 2019年5月17日
 */

@RestController
@RequestMapping("/signature")
public class KeyController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static  final  String OPEN_API="OPEN_API_";
    @Autowired
    IdWorker idWorker;
    @Autowired
    RedisUtil redisUtil;
    /**
     * 通过私钥生成签名
     *
     * <p>Title: keySign</p>    plaintext  ->appid+ timestamp  ; key :pritekey
     * <p>Description: </p>
     * @author zhanghc
     * @param
     * @return
     */
    @RequestMapping("/key/sign")
    public MsgInfo keySign(@RequestParam(value="plaintext", required=true) String plaintext,
                           @RequestParam(value="key", required=true) String key
    ) {
        MsgInfo msgInfo = new MsgInfo<>();
        try {
           // String plaintext = jsonObject.getString("plaintext");//数字签名所用字符串
          //  String key = jsonObject.getString("key");//数字签名所用字符串
            if(CommonUtil.isEmpty(plaintext)){
                return errorMsg(msgInfo, CodeEnum.PARAMS_FAILTURE.getCode(), "明文为空，请求失败！");
            }
            if(CommonUtil.isEmpty(key)){
                return errorMsg(msgInfo, CodeEnum.PARAMS_FAILTURE.getCode(), "秘钥为空，请求失败！");
            }
            String sign = ECDSAUtil.getPrivateKeySign(key, plaintext);
            msgInfo.setData(sign);
        } catch (Exception e) {
            logger.error("数字签名出错：",e);
            return errorMsg(msgInfo, CodeEnum.PARAMS_FAILTURE.getCode(), "参数不能为空！");
        }
        return msgInfo;
    }

    /**
     * 生成appid + privatekey +publickey
     * @return
     */
    @RequestMapping("/key/init")
    public MsgInfo initKey() {
       JSONObject jsonObject=new JSONObject();
        jsonObject.put("keytype",1);
        //jsonObject.put("appid",id);
        MsgInfo msgInfo = new MsgInfo<>();
        try {
            Integer keytype = jsonObject.getInteger("keytype");
            if(keytype==null) {
                return errorMsg(msgInfo, CodeEnum.PARAMS_FAILTURE.getCode(), "keytype不能为空！");
            }
            Long appid = jsonObject.getLong("appid");
            if(appid == null) {
                //新生成秘钥
                ECDSAKeyEntity keyEntity= ECDSAUtil.getECDSAKey();
                if(keyEntity!=null) {
                    String privateKey = keyEntity.getPrivateKey();//私钥，用于生成签名
                    String publicKey = keyEntity.getPublicKey();//公钥 用于校验签名
                    appid = idWorker.nextId();
                    redisUtil.set(OPEN_API+appid, publicKey);
                    redisUtil.set(OPEN_API+appid+privateKey,privateKey);
                    //新增秘钥表数据
                    SercretKey sercretKeyEntity = new SercretKey();
                    sercretKeyEntity.setAppid(appid);
                    sercretKeyEntity.setKeytype(keytype);
                    sercretKeyEntity.setPrivatekey(privateKey);
                    sercretKeyEntity.setPublickey(publicKey);
                    //sercretKeySerive.insert(sercretKeyEntity);
                    JSONObject jsonObject2 = new JSONObject();
                    jsonObject2.put("publicKey", publicKey);
                    jsonObject2.put("privateKey", privateKey);


                    jsonObject2.put("appid", appid);

                    msgInfo.setData(jsonObject2);
                }else {
                    return errorMsg(msgInfo, CodeEnum.PARAMS_FAILTURE.getCode(), "生成秘钥失败！");
                }
            }else {
                //修改秘钥
                ECDSAKeyEntity keyEntity= ECDSAUtil.getECDSAKey();
                if(keyEntity!=null) {
//                    String privateKey = keyEntity.getPrivateKey();//私钥，用于生成签名
//                    String publicKey = keyEntity.getPublicKey();//公钥 用于校验签名
//                    redisService.set(RedisConst.SIGNATURE+appid, publicKey);
//                    //修改秘钥表数据
//
//                    SercretKey sercretKeyEntity = new SercretKey();
//                    sercretKeyEntity.setAppid(appid);
//                    sercretKeyEntity.setKeytype(keytype);
//                    sercretKeyEntity.setPrivatekey(privateKey);
//                    sercretKeyEntity.setPublickey(publicKey);
//                    sercretKeySerive.update(sercretKeyEntity);
//
//                    JSONObject jsonObject2 = new JSONObject();
//                    jsonObject2.put("publicKey", publicKey);
//                    jsonObject2.put("privateKey", privateKey);
//                    jsonObject2.put("appid", appid);
//                    msgInfo.setData(jsonObject2);
                }else {
                    return errorMsg(msgInfo, CodeEnum.PARAMS_FAILTURE.getCode(), "生成秘钥失败！");
                }

            }



        } catch (Exception e) {
            logger.error("生成公钥，私钥出错：",e);
            return errorMsg(msgInfo, CodeEnum.PARAMS_FAILTURE.getCode(), "生成秘钥失败！");
        }
        return msgInfo;
    }
    MsgInfo<String> errorMsg(MsgInfo<String> msgInfo, String errorCode, String errorInfo){
        msgInfo.setCode(errorCode);
        msgInfo.setMessage(errorInfo);
        return msgInfo;
    }

    /**
     * 		验证签名
     *
     * <p>Title: initVerify</p>
     * <p>Description: </p>
     * @author zhanghc
     * @param jsonObject
     * @return
     */
    @PostMapping("/initVerify")
    public MsgInfo initVerify(@RequestBody JSONObject jsonObject ) {
        MsgInfo msgInfo = new MsgInfo<>();
        try {
            String plaintext = jsonObject.getString("plaintext");//数字签名所用字符串
            String sign = jsonObject.getString("sign");//数字签名
            Long appid = jsonObject.getLong("appid");//公钥关键字

            if(CommonUtil.isEmpty(plaintext)){
                return errorMsg(msgInfo, CodeEnum.PARAMS_FAILTURE.getCode(), "签名所用字符串为空，请求失败！");
            }
            if(CommonUtil.isEmpty(sign)){
                return errorMsg(msgInfo, CodeEnum.PARAMS_FAILTURE.getCode(), "数字签名为空，请求失败！");
            }
            if(null == appid){
                return errorMsg(msgInfo, CodeEnum.PARAMS_FAILTURE.getCode(), "appid为空，请求失败！");
            }
            String publicKey = redisUtil.get("OPEN_API-"+appid);
            if(CommonUtil.isEmpty(publicKey)){
                return errorMsg(msgInfo, CodeEnum.PARAMS_FAILTURE.getCode(), "密钥失效，请求重新获取密钥！");
            }
            //验证数据签名
            boolean flag =  ECDSAUtil.initVerify(publicKey, plaintext, sign);
            msgInfo.setData(flag);
        } catch (Exception e) {
            logger.error("验证数字签名出错：",e);
            return errorMsg(msgInfo, CodeEnum.PARAMS_FAILTURE.getCode(), "数字签名无效！");
        }
        return msgInfo;
    }
}
