package com.yanshen.util;

/**
 * <h3>spring-cloud</h3>
 * <p></p>
 *
 * @author : YanChao
 * @date : 2022-02-25 14:30
 **/
public class ThreadLocalUtils {

    public static final ThreadLocal<String> currentPlatform = new ThreadLocal<String>(); //当前用户所在平台
    public static final ThreadLocal<String> currentEnterpriseCode = new ThreadLocal<String>(); //当前用户企业编码
    public static final ThreadLocal<String> currentEnterpriseName = new ThreadLocal<String>();
    public static final ThreadLocal<String> currentUserName = new ThreadLocal<String>();
    public static final ThreadLocal<String> currentUserId = new ThreadLocal<String>();
    public static final ThreadLocal<StringBuilder> stringBuilder = new ThreadLocal<>();


    public static StringBuilder getStringBuilder() {
        return getStringBuilder(null);
    }

    public static StringBuilder getStringBuilder(String s) {
        StringBuilder sb = stringBuilder.get();
        if (sb == null) {
            sb = new StringBuilder();
            stringBuilder.set(sb);
        } else {
            sb.delete(0, sb.length());
        }
        if (s != null) {
            sb.append(s);
        }
        return sb;
    }

    public static void setCurrentUserId(String userId) {
        currentUserId.set(userId);
    }

    public static void setCurrentUserName(String userName) {
        currentUserName.set(userName);
    }

    public static void setCurrentPlatform(String platform) {
        currentPlatform.set(platform);
    }

    public static String getCurrentPlatform() {
        return currentPlatform.get();
    }

    public static void clearPlatform() {
        currentPlatform.remove();
        ;
    }

    public static void setCurrentEnterpriseCode(String enterCode) {
        currentEnterpriseCode.set(enterCode);
    }

    public static String getCurrentEnterpriseCode() {
        return currentEnterpriseCode.get();
    }

    public static void clearEnterpriseCode() {
        currentEnterpriseCode.remove();
    }

    public static void setCurrentEnterpriseName(String enterpriseName) {
        currentEnterpriseName.set(enterpriseName);
    }

    public static String getCurrentEnterpriseName() {
        return currentEnterpriseCode.get();
    }

    public static  String getCurrentUserId(){
        return currentUserId.get();
    }

}
