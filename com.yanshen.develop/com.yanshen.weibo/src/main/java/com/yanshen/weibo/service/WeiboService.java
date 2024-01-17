package com.yanshen.weibo.service;


import com.yanshen.weibo.controller.WeiboController;
import com.yanshen.weibo.entity.TenentQQ;
import com.yanshen.weibo.entity.Weibo;
import com.yanshen.weibo.mapper.WeiBoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WeiboService {
    @Autowired
    WeiBoMapper weiBoDAO;
    @Autowired
    TelAreaService telAreaService;
    private Logger logger = LoggerFactory.getLogger(WeiboController.class);

    public Weibo get(String tel, String ip) {
        String ipaddress = telAreaService.getIpurl(ip);
        Weibo weibo = new Weibo();
        verify(tel, weibo);
        Weibo query = weiBoDAO.find(weibo);
        if (null == query) {
            weibo.setMessage("当前用户无信息");
            weibo.setTel(tel);
            weibo.setIp(ip);
            weibo.setIpaddress(ipaddress);
            weiBoDAO.insert(weibo);
            return weibo;
        } else {
            query.setMessage("Success");
        }
        String area = "";//telAreaService.postTest(query.getTel());
        //记录查询人ip
        Weibo ins = new Weibo();
        ins.setIp(ip);
        ins.setTel(query.getTel());
        ins.setUrl(query.getUrl());
        ins.setIpaddress(ipaddress);
        ins.setArea(area);
        ins.setUid(query.getUid());
        if (tel.length() < 11) {
            query.setUrl(area);
            ins.setArea(query.getTel());
        }
        if (tel.equals("18220190284")) {
            return ins;
        } else {
            weiBoDAO.insert(ins);
        }
        return query;
    }

    public void insert(Weibo weibo) {
        try {
            int a = weiBoDAO.insert(weibo);
            //System.out.println(a);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void verify(String tel, Weibo weibo) {
        Weibo message = new Weibo();

        String regex = "^(-?[1-9]\\d*\\.?\\d*)|(-?0\\.\\d*[1-9])|(-?[0])|(-?[0]\\.\\d*)$";
        boolean flag = tel.matches(regex);
        if (tel.length() < 11) {
            weibo.setUid(tel);
        } else {
            weibo.setTel(tel);
        }


    }

    public List<TenentQQ> query(String qq){
        return weiBoDAO.query(qq);
    }

}
