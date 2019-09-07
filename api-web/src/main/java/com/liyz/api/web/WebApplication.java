package com.liyz.api.web;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 注释:web api 启动类
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/7 19:01
 */
//@EnableApolloConfig
@EnableDubbo
@SpringBootApplication(scanBasePackages = {"com.liyz.common", "com.liyz.api.web"})
public class WebApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(WebApplication.class);
        application.run(args);
    }
}
