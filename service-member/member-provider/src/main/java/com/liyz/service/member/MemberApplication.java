package com.liyz.service.member;

import com.liyz.common.redisson.annotation.EnableRedisson;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 注释: member启动类
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/7 14:57
 */
@EnableRedisson
@EnableScheduling
@MapperScan(basePackages = {"com.liyz.service.member.dao"})
@EnableDubbo(scanBasePackages = {"com.liyz.service.member.provider"})
@SpringBootApplication(scanBasePackages = {"com.liyz.common.base.util", "com.liyz.service.member"})
public class MemberApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MemberApplication.class);
        application.run(args);
    }
}
