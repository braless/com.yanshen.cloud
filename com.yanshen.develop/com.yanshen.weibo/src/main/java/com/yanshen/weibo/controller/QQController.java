package com.yanshen.weibo.controller;

import com.yanshen.weibo.entity.MsgInfo;
import com.yanshen.weibo.entity.TenentQQ;
import com.yanshen.weibo.service.WeiboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description:
 * @author: Yanchao
 * @create: 2021-02-27 11:14
 **/
@RestController("/")
public class QQController {
    @Autowired
    WeiboService weiboService;

    @GetMapping("qq/{qq}")
    public MsgInfo find(@PathVariable("qq") String qq, HttpServletRequest request) {

        List<TenentQQ> list = weiboService.query(qq);
        //测试
        //Test
        MsgInfo msgInfo = new MsgInfo();
        msgInfo.setData(list);
        return msgInfo;

    }
}
