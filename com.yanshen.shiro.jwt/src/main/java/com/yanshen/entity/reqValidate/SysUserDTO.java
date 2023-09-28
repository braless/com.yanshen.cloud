package com.yanshen.entity.reqValidate;

import com.yanshen.aspect.annotation.IDMust;
import lombok.Data;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-26 16:15
 * @Description:
 * @Location: com.yanshen.entity.reqValidate
 * @Project: com.yanshen.cloud
 */
@Data
public class SysUserDTO extends BaseReqValidate{


    private String userName;
    private Integer gender;
    @IDMust(message = "年龄必须大于0")
    private Integer age;
}
