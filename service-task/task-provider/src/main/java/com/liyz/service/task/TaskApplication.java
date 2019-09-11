package com.liyz.service.task;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 注释:task启动类
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/11 15:18
 */
@EnableAsync
@MapperScan(basePackages = {"com.liyz.service.task.dao"})
@EnableDubbo(scanBasePackages = {"com.liyz.service.task.provider"})
@SpringBootApplication(scanBasePackages = {"com.liyz.common", "com.liyz.service.task"})
public class TaskApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(TaskApplication.class);
        application.run(args);
    }
}
