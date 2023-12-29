package com.yanshen.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface AuthConstant {


    /**
     * 角色资源KEY
     */
    String SYS_ROLE_MENU_KEY = "SYS:ROLE:RESOURCE:KEY";

    String SYS_USER_DATA_KEY = "SYS:USER:PERMISSION:%s";

    /**
     * JWT存储权限前缀
     */
    String AUTHORITY_PREFIX = "ROLE_";

    /**
     * JWT存储权限属性
     */
    String AUTHORITY_CLAIM_NAME = "authorities";

    /**
     * 后台管理client_id
     */
    String ADMIN_CLIENT_ID = "admin-app";

    /**
     * 前台商城client_id
     */
    String PORTAL_CLIENT_ID = "portal-app";

    /**
     * 后台管理接口路径匹配
     */
    String ADMIN_URL_PATTERN = "/mall-admin/**";

    /**
     * Redis缓存权限规则key
     */
    String RESOURCE_ROLES_MAP_KEY = "auth:resourceRolesMap";

    /**
     * 认证信息Http请求头
     */
    String JWT_TOKEN_HEADER = "Authorization";

    /**
     * JWT令牌前缀
     */
    String JWT_TOKEN_PREFIX = "Bearer ";

    /**
     * 用户信息Http请求头
     */
    String USER_TOKEN_HEADER = "user";

    @Getter
    @AllArgsConstructor
    enum DataScopeEnum {

        /* 全部的数据权限 */
        ALL("全部", "全部的数据权限"),

        /* 自己部门的数据权限 */
        THIS_LEVEL("本级", "自己部门的数据权限"),

        /* 自定义的数据权限 */
        CUSTOMIZE("自定义", "自定义的数据权限");

        private final String value;
        private final String description;

        public static DataScopeEnum find(String val) {
            for (DataScopeEnum dataScopeEnum : DataScopeEnum.values()) {
                if (dataScopeEnum.getValue().equals(val)) {
                    return dataScopeEnum;
                }
            }
            return null;
        }

    }
}
