package com.yanshen.java.spring.autoload;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(YanshenImportSelector.class)
public @interface YanshenEnableAutoConfig {
}
