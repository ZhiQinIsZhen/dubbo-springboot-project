package com.liyz.common.security.config;

import com.liyz.common.base.remote.RemoteJwtUserService;
import com.liyz.common.security.core.JwtUserDetailsServiceImpl;
import com.liyz.common.security.util.LoginInfoUtil;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 注释: jwt bean 初始化
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/7 17:44
 */
@Configuration
public class JwtBeansConfigurer {

    @DubboReference(version = "1.0.0")
    RemoteJwtUserService remoteJwtUserService;

    @Autowired
    LoginInfoUtil loginInfoUtil;

    @Bean
    @ConditionalOnMissingBean(PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @ConditionalOnMissingBean(UserDetailsService.class)
    public UserDetailsService userDetailsService() {
        return new JwtUserDetailsServiceImpl(remoteJwtUserService, loginInfoUtil);
    }
}
