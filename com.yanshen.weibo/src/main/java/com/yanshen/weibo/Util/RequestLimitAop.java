package com.yanshen.weibo.Util;//package com.yanshen.weibo.Util;
//
//import org.apache.catalina.User;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.concurrent.TimeUnit;
//
//@Aspect
//@Component
//public class RequestLimitAop {
//    private static final Logger logger = LoggerFactory.getLogger("RequestLimitAop");
//
//    @Autowired
//    private RedisTemplate<String, User> redisTemplate;
//
//
//   /* @Before("within(@org.springframework.web.bind.annotation.RestController *) && @annotation(limit)")*/
//    public void requestLimit(final JoinPoint joinPoint, RequestLimit limit) throws RequestLimitException {
//
//        try {
//            Object[] args = joinPoint.getArgs();
//            HttpServletRequest request = null;
//            for (int i = 0; i < args.length; i++) {
//                if (args[i] instanceof HttpServletRequest) {
//                    request = (HttpServletRequest) args[i];
//                    break;
//                }
//            }
//            if (request == null) {
//                throw new RequestLimitException("方法中缺失HttpServletRequest参数");
//            }
//            String ip = request.getRemoteAddr();//HttpRequestUtil.getIpAddr(request);
//            String url = request.getRequestURL().toString();
//            String key = "req_limit_".concat(url).concat(ip);
//            long count = redisTemplate.opsForValue().increment(key, 1);
//            if (count == 1) {
//                redisTemplate.expire(key, limit.timeout(), TimeUnit.MILLISECONDS);
//            }
//            if (count > limit.maxCount()) {
//                logger.info("用户IP[" + ip + "]访问地址[" + url + "]超过了限定的次数[" + limit.maxCount() + "]");
//                throw new RequestLimitException();
//            }
//        } catch (RequestLimitException e) {
//            throw e;
//        } catch (Exception e) {
//            logger.error("发生异常: ", e);
//        }
//    }
//}