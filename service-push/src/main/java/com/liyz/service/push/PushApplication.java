package com.liyz.service.push;

import com.liyz.service.push.strap.BootStrap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 注释: push启动类
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/9/7 21:54
 */
@EnableScheduling
@SpringBootApplication
public class PushApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(PushApplication.class);
        ConfigurableApplicationContext context = application.run(args);

        BootStrap bootStrap = context.getBean(BootStrap.class);
        new Thread(bootStrap).start();
    }
}
