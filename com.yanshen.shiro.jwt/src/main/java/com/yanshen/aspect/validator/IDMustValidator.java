package com.yanshen.aspect.validator;

/**
 * @Auther: @Yanchao
 * @Date: 2023-09-26 16:01
 * @Description: 验证主键ID参数
 * @Location: com.yanshen.aspect.validator
 * @Project: com.yanshen.cloud
 */
import com.yanshen.aspect.annotation.IDMust;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
public class IDMustValidator implements ConstraintValidator<IDMust, Integer> {

    @Override
    public void initialize(IDMust constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null && value > 0;
    }
}
