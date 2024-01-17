package com.yanshen.aspect.annotation;

import com.yanshen.aspect.validator.StringContainsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-26 16:04
 * @Description:
 * @Location: com.yanshen.aspect.annotation
 * @Project: com.yanshen.cloud
 */
@Documented
@Constraint(validatedBy = StringContainsValidator.class)
@Target({ ElementType.PARAMETER,ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface StringContains {

    String message() default "字符串不符合规则";

    String[] values() default {};

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };

}