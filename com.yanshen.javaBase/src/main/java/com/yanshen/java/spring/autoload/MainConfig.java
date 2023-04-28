package com.yanshen.java.spring.autoload;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

//@Import(value = InstC.class)
@ComponentScan(basePackages = {"autoload"})
@Configuration
//@Import(value = {YanShenImportBeanDefinitionRegister.class})
@Import(value = {YanshenImportSelector.class})
public class MainConfig {
}
