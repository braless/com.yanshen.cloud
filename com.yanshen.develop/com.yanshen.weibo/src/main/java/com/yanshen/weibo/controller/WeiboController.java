package com.yanshen.weibo.controller;


import com.yanshen.weibo.entity.Weibo;
import com.yanshen.weibo.service.WeiboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static java.lang.System.out;

@Controller
@RequestMapping(value = "/weibo")
public class WeiboController {
  @Autowired
  WeiboService service;


  @GetMapping("/l")
  private String index() {
      return "weibo.html";
  }
    private Logger logger = LoggerFactory.getLogger(WeiboController.class);



    @RequestMapping(value="/get",method = {RequestMethod.POST, RequestMethod.GET})
    private ModelAndView login(HttpServletRequest request, HttpSession session) {
        ModelAndView mav=new ModelAndView();
        Weibo weibo = new Weibo();
        //out.print("ajax进入后台！！");
        String tel = request.getParameter("tel");
        if ("".equals(tel)){
            mav.setViewName("weibo");
            return mav;
        }
        logger.info("当前查询的用户是{}",tel);
        if (tel.length()>11||tel.length()<10){
            session.setAttribute("tel","手机号码格式不正确");
            mav.setViewName("weibo");
            return mav;
        }
        if (tel.length()==11){
            if (!tel.startsWith("1")){
                session.setAttribute("tel","手机号码格式不正确");
                mav.setViewName("weibo");
                return mav;
            }
        }
        if (tel.length()<11){
            weibo.setUid(tel);
        }else {
            weibo.setTel(tel);
        }

        Weibo tname = service.get(tel,"");
        out.print(tname);
        if (tname == null) {
            mav.clear();
            session.setAttribute("tel","当前用户没有查询到微博信息");
            mav.setViewName("weibo");
            return mav;
        } else {
            session.setAttribute("url", tname.getUrl());
            session.setAttribute("tel",tname.getTel());
            out.print(tname.getTel());
            //验证通过跳转首页
            mav.setViewName("weibo");
            return mav;
        }
    }
}
