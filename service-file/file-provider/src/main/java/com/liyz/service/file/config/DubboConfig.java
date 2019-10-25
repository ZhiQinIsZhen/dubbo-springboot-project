package com.liyz.service.file.config;

import org.apache.dubbo.config.ProtocolConfig;
import org.apache.dubbo.remoting.http.servlet.DispatcherServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/10/24 18:54
 */
@Configuration
public class DubboConfig {

    @Bean(name = "hessian")
    public ProtocolConfig protocolConfig() {
        ProtocolConfig protocolConfig = new ProtocolConfig();
        protocolConfig.setId("hessian");
        protocolConfig.setName("hessian");
        protocolConfig.setPort(8099);
        protocolConfig.setServer("servlet");
        protocolConfig.setContextpath(null);
        return protocolConfig;
    }

    @Bean
    public ServletRegistrationBean<DispatcherServlet> servletRegistrationBean() {
        return new ServletRegistrationBean<DispatcherServlet>(new DispatcherServlet(), "/*");

    }
}
