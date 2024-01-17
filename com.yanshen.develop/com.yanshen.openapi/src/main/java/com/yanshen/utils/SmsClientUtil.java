package com.yanshen.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SmsClientUtil {

    private static  final Logger LOG = LoggerFactory.getLogger(SmsClientUtil.class);

    public static String sendPush(SmsEntity sms) {
        try {
            StringBuilder url = new StringBuilder(CodeEnum.SMSURL.getCode());
            url.append("&mobile=");
            url.append(String.join(",", sms.getTargetPhone()));
            url.append("&sms=");
            url.append(URLEncoder.encode(sms.getContent(),"GBK"));
            URL uri = new URL(url.toString());
            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            byte[] bytes = new byte[1024 * 2000];
            int index = 0;
            int count = inputStream.read(bytes, index, 1024 * 2000);
            while (count != -1) {
                index += count;
                count = inputStream.read(bytes, index, 1);
            }
            String htmlContent = new String(bytes, "UTF-8");
            LOG.info(sms.getTargetPhone()+"----"+sms.getContent());
            LOG.info(url.toString());
            connection.disconnect();
            return htmlContent.trim();
        } catch (Exception e) {
            LOG.error("",e);
            return null;
        }
    }
}
