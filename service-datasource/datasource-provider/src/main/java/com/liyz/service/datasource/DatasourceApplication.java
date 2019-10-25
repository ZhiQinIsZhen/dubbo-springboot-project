package com.liyz.service.datasource;

import com.liyz.common.redisson.annotation.EnableRedisson;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 注释: datasource 启动类
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/7 23:42
 */
@EnableRedisson
@EnableScheduling
@MapperScan(basePackages = {"com.liyz.service.datasource.dao"})
@EnableDubbo(scanBasePackages = {"com.liyz.service.datasource.provider"})
@SpringBootApplication(scanBasePackages = {"com.liyz.common.base.util", "com.liyz.service.datasource"})
public class DatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatasourceApplication.class, args);
    }
}
