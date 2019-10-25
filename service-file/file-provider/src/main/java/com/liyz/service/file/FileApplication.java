package com.liyz.service.file;

import com.liyz.common.redisson.annotation.EnableRedisson;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * 注释:文件服务器启动类
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/10/24 13:53
 */
@EnableRedisson
@EnableScheduling
@MapperScan(basePackages = {"com.liyz.service.file.dao"})
@EnableDubbo(scanBasePackages = {"com.liyz.service.file.provider"})
@SpringBootApplication(scanBasePackages = {"com.liyz.common.base.util", "com.liyz.service.file"})
public class FileApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileApplication.class, args);
    }
}
