package com.yanshen.weibo.controller;


import com.yanshen.weibo.Util.RedisUtil;
import com.yanshen.weibo.entity.Weibo;
import com.yanshen.weibo.service.TelAreaService;
import com.yanshen.weibo.service.WeiboService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <h3>cyc_spring</h3>
 * <p>RestFul</p>
 *
 * @author : YanChao
 * @date : 2020-09-02 10:33
 **/

@CrossOrigin
@RestController
@RequestMapping("/weibo")
public class JsonWeiboController {
    @Autowired
    WeiboService weiboService;
    @Resource
    TelAreaService telAreaService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RedisUtil redisUtil;
    private Logger logger = LoggerFactory.getLogger(WeiboController.class);

    @GetMapping("/tel/{tel}")
    public Weibo find(@PathVariable("tel") String tel, HttpServletRequest request) {
        String ip = getip(request);
        logger.info("当前ip是:{}", ip);
//        if (!ip.equals("60.191.75.18") && !ip.equals("122.224.174.250")) {
//            Weibo weibo = checklimit(ip, tel);
//            if (weibo.getMessage() != "Success") {
//                return weibo;
//            }
//        }
        Weibo weibo = new Weibo();
        if (tel.length() < 10) {
            weibo.setMessage("当前用户无信息");
            weibo.setTel(tel);
            return weibo;
        }
        Weibo query = weiboService.get(tel, ip);
        return query;
    }


    public Weibo checklimit(String ip, String tel) {
        Weibo weibo = new Weibo();
        String islimit = redisUtil.get("islimit");
        if ("1".equals(islimit)) {
            String value = redisUtil.get(ip + "MinLimte");
            String dayValue = redisUtil.get(ip + "DayLimte");
            if (value == null) {
                redisUtil.set(ip + "MinLimte", 1);
                redisUtil.expire(ip + "MinLimte", 60);//设置过期时间60秒
                if (dayValue == null) {
                    redisUtil.set(ip + "DayLimte", 1);
                    redisUtil.expire(ip + "DayLimte", 86400);//设置过期时间24hours
                } else {
                    redisUtil.incr(ip + "DayLimte", 1);  //加一次
                    int parseIntDay = Integer.parseInt(dayValue);
                    if (parseIntDay > 5) {
                        weibo.setMessage("一天内只有5次机会哦");
                        return weibo;
                    }
                }
            } else {
                int parseInt = Integer.parseInt(value);
                if (dayValue != null) {
                    int daylimit = Integer.parseInt(dayValue);
                    if (daylimit > 5) {
                        weibo.setMessage("一天内只有5次机会哦");
                        weibo.setTel(tel);
                        weibo.setIp(ip);
                        return weibo;
                    }
                }
                if (parseInt >= 1) {
                    weibo.setMessage("1分钟内只能查询一次");
                    weibo.setTel(tel);
                    weibo.setIp(ip);
                    return weibo;
                }
                redisUtil.incr(ip + "DayLimte", 1);
            }
            //
            weibo.setMessage("Success");
            weibo.setIp(ip);
            weibo.setTel(tel);
        } else {
            weibo.setMessage("Success");
            weibo.setIp(ip);
            weibo.setTel(tel);
        }
        return weibo;
    }


    private String getip(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
