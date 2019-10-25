package com.liyz.api.web.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.google.common.collect.Sets;
import com.liyz.common.controller.config.SwaggerBaseConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 注释:swagger配置
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/7/16 16:30
 */
@EnableSwaggerBootstrapUI
@EnableSwagger2
@Configuration
public class SwaggerConfigurer extends SwaggerBaseConfigurer {

    @Bean
    public Docket createAuthApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .protocols(Sets.newHashSet("https", "http"))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.liyz.api.web.controller.auth"))
                .paths(PathSelectors.any())
                .build().groupName("鉴权认证-API");
    }

    @Bean
    public Docket createBusinessApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .protocols(Sets.newHashSet("https", "http"))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.liyz.api.web.controller.business"))
                .paths(PathSelectors.any())
                .build().groupName("业务-API");
    }

    @Bean
    public Docket createErrorApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .protocols(Sets.newHashSet("https", "http"))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.liyz.api.web.controller.error"))
                .paths(PathSelectors.any())
                .build().groupName("错误-API");
    }

    @Bean
    public Docket createFileApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .protocols(Sets.newHashSet("https", "http"))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.liyz.api.web.controller.file"))
                .paths(PathSelectors.any())
                .build().groupName("文件服务-API");
    }
}
