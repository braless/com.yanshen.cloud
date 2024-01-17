package com.yanshen.aspect.annotation;

import com.yanshen.aspect.validator.IDMustValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-26 16:02
 * @Description:
 * @Location: com.yanshen.aspect.annotation
 * @Project: com.yanshen.cloud
 */

@Documented
@Constraint(validatedBy = IDMustValidator.class)
@Target({ ElementType.PARAMETER,ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface IDMust {

    String message() default "id参数必须存在且大于0";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default { };

}

