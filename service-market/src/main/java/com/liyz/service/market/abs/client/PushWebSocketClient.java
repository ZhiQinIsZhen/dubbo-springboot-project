package com.liyz.service.market.abs.client;

import com.liyz.service.market.abs.AbstractWebSocketClient;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/10/10 18:47
 */
public class PushWebSocketClient extends AbstractWebSocketClient {

    public PushWebSocketClient() {
    }

    public PushWebSocketClient(boolean useEpoll) {
        super(useEpoll);
    }

    @Override
    protected String getUrl() {
        return "ws://127.0.0.1:9999/websocket";
    }

    /**
     * {op:"heartbeat"}
     */
    @Override
    public void sendPing() {
        if (isAlive()) {
            getChannel().writeAndFlush(new TextWebSocketFrame("{op:\"heartbeat\"}"));
        }
    }

    @Override
    public void reConnect() {

    }
}
