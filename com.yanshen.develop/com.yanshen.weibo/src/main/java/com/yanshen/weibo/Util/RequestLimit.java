package com.yanshen.weibo.Util;//package com.yanshen.weibo.Util;
//
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//
//import java.lang.annotation.*;
//
//@Retention(RetentionPolicy.RUNTIME)
//@Target(ElementType.METHOD)
//@Documented
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public @interface RequestLimit {
//
//    /**
//     * 接口的最大访问次数
//     * @author wangjie
//     * @date 2017/10/12
//     */
//    int maxCount() default Integer.MAX_VALUE;
//
//    /**
//     * 规定统计的时间段，默认一分钟
//     * @author wangjie
//     * @date 2017/10/12
//     */
//    long timeout() default 60 * 1000;
//}