package com.yanshen.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.*;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @Auther: @Yanchao
 * @Date: 2023-11-09 15:13
 * @Description:
 * @Location: com.yanshen.config
 * @Project: com.yanshen.cloud
 */
@Configuration
@EnableOpenApi
public class Swagger2Config {
    /**
     * 用于读取配置文件 application.properties 中 swagger 属性是否开启
     */
    //@Value("${swagger.enabled}")
    Boolean swaggerEnabled=true;

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                // 是否开启swagger
                .enable(swaggerEnabled)
                .select()
                // 过滤条件，扫描指定路径下的文件
                .apis(RequestHandlerSelectors.basePackage("com.yanshen.controller"))
                // 指定路径处理，PathSelectors.any()代表不过滤任何路径
                //.paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        /*作者信息*/
        Contact contact = new Contact("村雨遥", "https://cunyu1943.github.io", "747731461@qq.com");
        return new ApiInfo(
                "Spring Boot 集成 Swagger3 测试",
                "Spring Boot 集成 Swagger3 测试接口文档",
                "v1.0",
                "https://cunyu1943.github.io",
                contact,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList()
        );
    }
}
