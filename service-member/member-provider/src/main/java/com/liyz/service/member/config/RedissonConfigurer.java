package com.liyz.service.member.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SentinelServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 注释:redisson配置
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/7/22 9:48
 */
@Slf4j
@Configuration
public class RedissonConfigurer {

    @Value("${spring.redisson.url}")
    private String url;

    @Value("${spring.redis.datasource}")
    private int datasource;

    @Value("${spring.redis.password}")
    private String password;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        if (url.indexOf(",") != -1) {
            SentinelServersConfig ssc = config.useSentinelServers().addSentinelAddress(url.split(",")).setDatabase(datasource);
            //如果是主从关系，则上面要加上哪个是master
            if(StringUtils.isNotBlank(password)){
                ssc.setPassword(password);
            }
        } else {
            SingleServerConfig ssc = config.useSingleServer()
                    .setAddress(url)
                    .setDatabase(datasource);
            if(StringUtils.isNotBlank(password)){
                ssc.setPassword(password);
            }
        }
        return Redisson.create(config);
    }
}
