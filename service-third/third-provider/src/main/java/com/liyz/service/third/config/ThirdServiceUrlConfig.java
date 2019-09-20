package com.liyz.service.third.config;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/18 13:44
 */
@Slf4j
@Getter
@Configuration
public class ThirdServiceUrlConfig {

    /**
     * 企查查服务信息
     */
    @Value("${qcc.url}")
    private String qccUrl;
    @Value("${qcc.key}")
    private String qccKey;
    @Value("${qcc.secret.key}")
    private String qccSecretKey;
}
