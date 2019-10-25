package com.liyz.service.third;

import com.liyz.common.redisson.annotation.EnableRedisson;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 注释:第三方调用服务启动类
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/20 15:05
 */
@EnableRedisson
@EnableScheduling
@MapperScan(basePackages = {"com.liyz.service.third.dao"})
@EnableDubbo(scanBasePackages = {"com.liyz.service.third.provider"})
@SpringBootApplication(scanBasePackages = {"com.liyz.common.base.util", "com.liyz.service.third"})
public class ThirdApplication {

    public static void main(String[] args) {
        SpringApplication.run(ThirdApplication.class, args);
    }
}
