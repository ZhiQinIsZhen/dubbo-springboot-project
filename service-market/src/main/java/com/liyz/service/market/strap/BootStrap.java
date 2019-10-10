package com.liyz.service.market.strap;

import com.liyz.service.market.abs.AbstractWebSocketClient;
import com.liyz.service.market.abs.client.PushWebSocketClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 注释: netty 启动线程
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/10/8 13:58
 */
@Slf4j
@Component
public class BootStrap implements Runnable {

    @Value("${netty.use.epoll}")
    private boolean useEpoll;

    @Override
    public void run() {
        AbstractWebSocketClient client = new PushWebSocketClient(useEpoll);
        client.start();
    }

}
