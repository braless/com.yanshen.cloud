package com.yanshen.dto;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * @Classname SysUserConstant
 * @Description
 * @Date 2022/11/24 18:15
 * @Author wanglei
 */
public interface SysUserConstant {

    @Getter
    enum GenderEnum {
        MALE("1", "男"),
        FEMALE("2", "女");

        private String code;

        private String desc;

        private GenderEnum(String code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static String getDesc(String code){
            Optional<GenderEnum> optional = Arrays.stream(GenderEnum.values()).filter(item -> Objects.equals(code, item.code)).findFirst();
            if(optional.isPresent()){
                return optional.get().getDesc();
            }
            return "";
        }

    }

}