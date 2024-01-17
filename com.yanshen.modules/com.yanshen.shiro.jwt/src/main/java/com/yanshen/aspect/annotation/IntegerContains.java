package com.yanshen.aspect.annotation;

import com.yanshen.aspect.validator.IntegerContainsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-26 15:59
 * @Description:
 * @Location: com.yanshen.aspect.annotation
 * @Project: com.yanshen.cloud
 */

@Documented
@Constraint(validatedBy = IntegerContainsValidator.class)
@Target({ ElementType.PARAMETER,ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IntegerContains {

    String message() default "数值不符合规则";

    int[] values() default {};

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };

}
