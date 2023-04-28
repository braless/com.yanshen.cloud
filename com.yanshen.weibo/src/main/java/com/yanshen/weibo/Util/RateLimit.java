package com.yanshen.weibo.Util;/*
package com.yanshen.weibo.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;


*/
/**
 * <p>Title:</p>
 *
 * @Title: allow @Description: 进行流量控制,允许访问返回true 不允许访问返回false
 * @param: @param key 放入redis的key，放入前要在key之前添加前缀 前缀配置在eds.properties中的 redis.prefix
 * @param: @param timeOut 超时时间单位秒
 * @param: @param count 超时时间内允许访问的次数
 * @param: @param type 不同类型的数据
 * @param: @return
 * @param: @throws
 * Exception @return: boolean @throws
 *//*

public class RateLimit {



    private static final Logger logger = LoggerFactory.getLogger(RateLimit.class);

    private static final String RATE_LIMIT = "RATELIMIT";

    */
/**
 * @Title: allow @Description: 进行流量控制,允许访问返回true 不允许访问返回false
 * @param: @param key 放入redis的key，放入前要在key之前添加前缀 前缀配置在eds.properties中的 redis.prefix
 * @param: @param timeOut 超时时间单位秒
 * @param: @param count 超时时间内允许访问的次数
 * @param: @param type 不同类型的数据
 * @param: @return
 * @param: @throws
 *             Exception @return: boolean @throws
 *//*

    public static boolean allow(String type,String key, int timeOut, int count) {

//        Boolean useFc = Boolean.valueOf(EdsPropertiesUtil.getInstance().getProperty("flowControl.use"));
//        // 若不使用流量控制直接返回true
//        if (!useFc) {
//            return true;
//        }

        boolean result = false;
        Jedis jedis = null;
        StringBuffer keyBuff = new StringBuffer(RATE_LIMIT);
        keyBuff.append("_").append(type).append(":").append(key);
        key = keyBuff.toString();
        try {
            jedis = new Jedis("192.168.30.120", Integer.valueOf("6379"));
            jedis.connect();
            Long newTimes = null;
            Long pttl = jedis.pttl(key);

            if (pttl > 0) {
                newTimes = jedis.incr(key);
                if (newTimes > count) {
                    logger.info("key:{},超出{}秒内允许访问{}次的限制,这是第{}次访问", new Object[] { key, timeOut, count, newTimes });
                } else {
                    result = true;
                }
            } else if (pttl == -1 || pttl == -2 || pttl == 0) {

                Transaction tx = jedis.multi();
                Response<Long> rsp1 = tx.incr(key);
                tx.expire(key, timeOut);
                tx.exec();
                newTimes = rsp1.get();
                if (newTimes > count) {
                    logger.info("key:{},{}秒内允许访问{}次,第{}次访问", new Object[] { key, timeOut, count, newTimes });
                } else {

                    result = true;
                }

            }
            if (result) {
                logger.debug("key:{},访问次数{}", new Object[] { key, newTimes });
            }

        } catch (Exception e) {
            logger.error("流量控制发生异常", e);
            e.printStackTrace();
            // 当发生异常时 允许访问
            result = true;
        } finally {
            jedis.close();
        }

        return result;

    }
}*/
