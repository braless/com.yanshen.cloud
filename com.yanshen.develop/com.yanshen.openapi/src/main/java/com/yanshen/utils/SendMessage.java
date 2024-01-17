package com.yanshen.utils;

import java.util.ArrayList;
import java.util.List;

public class SendMessage {
    public static void main(String[] args) {
        SmsEntity smsEntity =new SmsEntity();
        List<String>  tel=new ArrayList<>();
        tel.add("13165963563");
        smsEntity.setTargetPhone(tel);
        smsEntity.setContent("ceshi");
        SmsClientUtil.sendPush(smsEntity);
    }
}
