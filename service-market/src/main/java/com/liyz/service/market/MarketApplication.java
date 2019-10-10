package com.liyz.service.market;

import com.liyz.service.market.strap.BootStrap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 注释:启动类
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/10/8 11:32
 */
@EnableScheduling
@SpringBootApplication
public class MarketApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(MarketApplication.class);
        ConfigurableApplicationContext context = application.run(args);

        BootStrap bootStrap = context.getBean(BootStrap.class);
        new Thread(bootStrap).start();
    }
}
