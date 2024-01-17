package com.yanshen.entity.reqValidate;

import com.yanshen.aspect.annotation.IntegerContains;
import com.yanshen.aspect.annotation.StringContains;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-21 14:30
 * @Description:
 * @Location: com.yanshen.entity.dto
 * @Project: com.yanshen.cloud
 */
@Data
public class LoginDTO {


    /**
     * 账号
     */
    @NotEmpty(message = "账号不能为空",groups = LoginDTO.class)
    private String userName;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空",groups = LoginDTO.class)
    @Length(min = 6, max = 32, message = "密码必须在6~32个字符内",groups = LoginDTO.class)
    private String password;

    /**
     * 验证码
     */
    private String captcha;

    /**
     * 验证码key
     */
    private String checkKey;

    @IntegerContains(values = {10, 20, 30}, message = "type不在合法值内")
    private Integer feetSize;


    @NotNull(message = "menuType参数缺失")
    @StringContains(values = {"M", "C", "A"}, message = "菜单类型不是合法值(M,C,A)")
    private String menuType;
}
