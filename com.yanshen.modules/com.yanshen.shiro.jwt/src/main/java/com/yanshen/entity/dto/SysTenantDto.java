package com.yanshen.entity.dto;

import lombok.Data;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-19 17:55
 * @Description:
 * @Location: com.yanshen.entity.dto
 * @Project: com.yanshen.cloud
 */
@Data
public class SysTenantDto {

    private String expireAt;
    private String token;

}
