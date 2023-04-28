package com.yanshen.dev.tutils;

import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.*;

/**
 * @author LXJ 2020/7/7 13:53
 * @description
 */
public class MD5Util {

    /***
     * MD5加码 生成32位md5
     */
    public static String stringToMD5(String inStr) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
            return "";
        }
        char[] charArray = inStr.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];
        byte[] md5Bytes = md5.digest(byteArray);
        StringBuffer hexValue = new StringBuffer();
        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();

    }

    /**
     * 创建md5摘要,规则是:按参数名称a-z排序,遇到空值的参数不参加签名。
     */
    public static String createSign(Map<String, String> map, String key) {
        StringBuffer sb = new StringBuffer();
        List<String> keyList = new ArrayList<String>(map.keySet());
        Collections.sort(keyList);
        String str;
        for (String s : keyList) {
            if (!"key".equalsIgnoreCase(s)) {
                str = String.valueOf(map.get(s));
                if (StringUtils.isEmpty(str) || "sign".equals(s)) {
                    continue;
                }
                sb.append(s + "=" + str + "&");
            }
        }

        sb.append("key=" + key);
        System.out.println("最终字符串： ");
        System.out.println(sb.toString());
        String sign = DigestUtils.md5Hex(getContentBytes(sb.toString(), "utf-8"));
        return sign.toUpperCase();
    }

    /**
     * key 从 a - Z
     */
    public static String createQueryString(Map<String, String> map) {
        StringBuffer sb = new StringBuffer();
        List<String> keyList = new ArrayList<String>(map.keySet());
        int len = keyList.size();
        Collections.sort(keyList);
        String str;
        for (int i = 0; i < len; i++) {
            String s = keyList.get(i);
            str = String.valueOf(map.get(s));
            if (i != len - 1) {
                sb.append(s + "=" + str + "&");
            } else {
                sb.append(s + "=" + str);
            }
        }
        return sb.toString();
    }

    /**
     * @param content
     * @param charset
     */
    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
        }
    }

    /**
     * 校验签名
     *
     * @param map
     * @param key
     * @return
     */
    public static boolean verfySign(Map<String, String> map, String key) {
        String sign = (String) map.get("sign");
        String resSign = createSign(map, key);
        System.out.println("====传来的sign: " + sign);
        System.out.println("====生成的sign: " + resSign);
        return sign != null && sign.equalsIgnoreCase(resSign);
    }

    public static void generatorReqStr() {
        // //生成TUSN 规则 厂商编码（000065）+类型id（02）+型号名称（V90）+序列号（9位：000000011）

        String accessKeyId = "JCAI0JLgqMk3dUji";
        String accessKey = "OwD5mVgQvVR28lsJ3utY7qkFXMjjUi";

        Map<String, String> map = new HashMap<>();
        String signatureNonce = UUID.randomUUID().toString();
        String timestamp = String.valueOf(System.currentTimeMillis());

        map.put("action", "fetchTsnInfo");
        map.put("factoryNo", "FCT202007071549000003");
        map.put("accessKeyId", accessKeyId);
        map.put("signatureNonce", signatureNonce);
        map.put("timestamp", timestamp);
        map.put("tsn", "TUSN2020070308550001");
        // 新增 客户编号 ， 项目名称编号
        // 客户编号     CS2020070210070001
        // 项目名称编号 PR202007151012431076
        map.put("custNo", "CS2020070210070001");
        map.put("proNo", "PR202007151012431076");


        System.out.println(JSON.toJSONString(map, true));

        String sign = createSign(map, accessKey);
        System.out.println("sign : ");
        System.out.println(sign);

        /**
         {
         "accessKeyId":"JCAI0JLgqMk3dUji",
         "proNo":"PR202007151012431076",
         "custNo":"CS2020070210070001",
         "factoryNo":"FCT202007071549000003",
         "action":"fetchTsnInfo",
         "tusn":"TUSN2020070308550001",
         "signatureNonce":"8595e943-f674-49ed-9eb7-310172f6aba9",
         "timestamp":"1594880359624"
         }
         最终字符串：
         accessKeyId=JCAI0JLgqMk3dUji&action=fetchTsnInfo&custNo=CS2020070210070001&factoryNo=FCT202007071549000003&proNo=PR202007151012431076&signatureNonce=8595e943-f674-49ed-9eb7-310172f6aba9&timestamp=1594880359624&tusn=TUSN2020070308550001&key=OwD5mVgQvVR28lsJ3utY7qkFXMjjUi
         sign :
         1964916964310CB8DC2E1C5BF780FA2C
         */

    }

    public static void verifyReq() {
        String accessKeyId = "JCAI0JLgqMk3dUji";
        String accessKey = "OwD5mVgQvVR28lsJ3utY7qkFXMjjUi";

        Map<String, String> map = new HashMap<>();

        map.put("action", "fetchTsnInfo");
        map.put("factoryNo", "FCT202007071549000003");
        map.put("accessKeyId", accessKeyId);
        map.put("signatureNonce", "8595e943-f674-49ed-9eb7-310172f6aba9");
        map.put("timestamp", "1594880359624");
        map.put("tsn", "TUSN2020070308550001");
        map.put("custNo", "CS2020070210070001");
        map.put("proNo", "PR202007151012431076");
        map.put("sign", "1964916964310CB8DC2E1C5BF780FA2C");


        System.out.println(createQueryString(map));


//        System.out.println(JSON.toJSONString(map, true));

        //  accessKeyId=LTAI0JLgqMk3dUjn&action=fetchTusnInfo&factoryNo=FCT202007071549000003&sign=2C2384997D7969F40C15BCFB9A7381C1&signatureNonce=2f47ecf5-1ab0-42e0-bd3c-6ff7f24c884d&timestamp=1594178109344&tusn=TUSN2020070308550001


        boolean flag = verfySign(map, accessKey);
        System.out.println(flag);

    }

    public static String generAccessKeyId() {
        String[] uuid = UUID.randomUUID().toString().replace("-", "").split("");
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < uuid.length; i++) {
            if (i % 2 == 0) {
                str.append(uuid[i].toUpperCase());
            } else {
                str.append(uuid[i]);
            }
        }
        String keyId = "JC" + str.toString().substring(0, 14);
        return keyId;
    }

    public static String generAccessKey() {
        String[] uuid = UUID.randomUUID().toString().replace("-", "").split("");
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < uuid.length; i++) {
            if (i % 2 == 0) {
                str.append(uuid[i].toUpperCase());
            } else {
                str.append(uuid[i]);
            }
        }
        return str.toString();
    }


    public static void main(String[] args) throws Exception {

//        generatorReqStr();

        verifyReq();

        /*
        AccessKey ID	     AccessKey Secret
        LTAI0JLgqMk3dUjn	OwD5mVgQvVR28lsJ3utY7qkFXMjjUb

        Action              API名称
        factoryNo           工厂编号
        AccessKeyId         AccessKey ID 访问密钥 ID。AccessKey 用于调用 API。
        SignatureNonce      随机字符串 JAVA语言建议用：java.util.UUID.randomUUID()生成。
        Timestamp           请求的时间戳。按照ISO8601 标准表示，并需要使用UTC时间，格式为yyyy-MM-ddTHH:mm:ssZ。示例：2018-01-01T12:00:00Z
                            表示北京时间 2018 年 01 月 01 日 20 点 00 分 00 秒。
        tusn                 tusn
        sign                MD5签名


        {
            "accessKeyId":"LTAI0JLgqMk3dUjn",
            "factoryNo":"FCT202007071549000003",
            "action":"fetchTusnInfo",
            "tusn":"TUSN2020070308550001",
            "signatureNonce":"074c3d24-8a16-4c9c-b541-e9c5231a3da6",
            "timestamp":"1594108821986"
        }
        sb
        accessKeyId=LTAI0JLgqMk3dUjn&action=fetchTusnInfo&factoryNo=FCT202007071549000003&signatureNonce=074c3d24-8a16-4c9c-b541-e9c5231a3da6&timestamp=1594108821986&tusn=TUSN2020070308550001&key=OwD5mVgQvVR28lsJ3utY7qkFXMjjUb
        sign
        9b4f5bafbf8f9802600a5d6829a0767a
        sign :
        9B4F5BAFBF8F9802600A5D6829A0767A
        * */


//        testDemo();
        /*
        http://dysmsapi.aliyuncs.com/?Signature=wMoUu6ngtL%2F9gZLq8uaIYffnkoQ%3D&AccessKeyId=testId&Action=SendSms&Format=XML&OutId=123&PhoneNumbers=15300000001&RegionId=cn-hangzhou&SignName=%E9%98%BF%E9%87%8C%E4%BA%91%E7%9F%AD%E4%BF%A1%E6%B5%8B%E8%AF%95%E4%B8%93%E7%94%A8&SignatureMethod=HMAC-SHA1&SignatureNonce=0534106d-961f-4eb5-9c52-cc6af76b7c6d&SignatureVersion=1.0&TemplateCode=SMS_71390007&TemplateParam=%7B%22customer%22%3A%22test%22%7D&Timestamp=2020-07-07T07%3A01%3A02Z&Version=2017-05-25

        http://dysmsapi.aliyuncs.com/?
        Signature=wMoUu6ngtL%2F9gZLq8uaIYffnkoQ%3D&
        AccessKeyId=testId&
        Action=SendSms&
        Format=XML&
        OutId=123&
        PhoneNumbers=15300000001&
        RegionId=cn-hangzhou&
        SignName=%E9%98%BF%E9%87%8C%E4%BA%91%E7%9F%AD%E4%BF%A1%E6%B5%8B%E8%AF%95%E4%B8%93%E7%94%A8&
        SignatureMethod=HMAC-SHA1&
        SignatureNonce=0534106d-961f-4eb5-9c52-cc6af76b7c6d&
        SignatureVersion=1.0&
        TemplateCode=SMS_71390007&
        TemplateParam=%7B%22customer%22%3A%22test%22%7D&
        Timestamp=2020-07-07T07%3A01%3A02Z&
        Version=2017-05-25

        * */
    }

    /*

        AccessKey ID	     AccessKey Secret
        LTAI0JLgqMk3dUjn	OwD5mVgQvVR28lsJ3utY7qkFXMjjUb

        例子：
        https://dysmsapi.aliyuncs.com/?Action=SendSms&<公共请求参数>
        https 指定了请求通信协议。
        dysmsapi.aliyuncs.com 指定了短信服务的服务接入地址（Endpoint）。
        Action=SendSms 指定了要调用的API。
        <公共请求参数> 是系统规定的其他公共参数。

        名称	        类型	    是否必填	            说明
        Signature	    String	    是	                请求签名，即最终生成的签名结果值。
        如何生成请求签名，请查看请求签名。

        AccessKeyId	    String	    是	                访问密钥 ID。AccessKey 用于调用 API。
        Action	        String	    是	                API 的名称。
        Format	        String	    否	                返回参数的语言类型。取值范围：json | xml。默认值：json。
        RegionId	    String	    否	                API支持的RegionID，如短信API的值为：cn-hangzhou。
        SignatureMethod	String	    是	                签名方式。取值范围：HMAC-SHA1。
        SignatureNonce	String	    是	                签名唯一随机数。用于防止网络重放攻击，建议您每一次请求都使用不同的随机数。

        JAVA语言建议用：java.util.UUID.randomUUID()生成。

        SignatureVersion	String	是	                签名算法版本。取值范围：1.0。
        Timestamp	    String	    是	                请求的时间戳。按照ISO8601 标准表示，并需要使用UTC时间，格式为yyyy-MM-ddTHH:mm:ssZ。示例：2018-01-01T12:00:00Z 表示北京时间 2018 年 01 月 01 日 20 点 00 分 00 秒。
        Version	        String	    是	                API 的版本号，格式为 YYYY-MM-DD。取值范围：2017-05-25。



        Action              API名称
        factoryNo           工厂编号
        AccessKeyId         AccessKey ID 访问密钥 ID。AccessKey 用于调用 API。
        SignatureNonce      随机字符串 JAVA语言建议用：java.util.UUID.randomUUID()生成。
        Timestamp           请求的时间戳。按照ISO8601 标准表示，并需要使用UTC时间，格式为yyyy-MM-ddTHH:mm:ssZ。示例：2018-01-01T12:00:00Z
                            表示北京时间 2018 年 01 月 01 日 20 点 00 分 00 秒。
        tusn                 tusn
        sign                MD5签名
    */

}

