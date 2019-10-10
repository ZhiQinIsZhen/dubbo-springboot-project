package com.liyz.service.market.abs;

import com.liyz.service.market.handler.WebSocketChannelInitializer;
import com.liyz.service.market.util.ChannelUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.DefaultThreadFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.nio.channels.UnsupportedAddressTypeException;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/10/10 17:51
 */
@Slf4j
public abstract class AbstractWebSocketClient {

    @Getter
    private Channel channel;

    private EventLoopGroup workGroup = null;
    private URI uri;
    private String host;
    private int port;

    private boolean useEpoll = false;

    public AbstractWebSocketClient() {
    }

    public AbstractWebSocketClient(boolean useEpoll) {
        this.useEpoll = useEpoll;
    }

    private static final String CHANNEL_KEY = "local";

    /**
     * 启动
     */
    public void start() {
        log.info("market server start ...");
        try {
            Class channelClass;
            if (useEpoll) {
                workGroup = new EpollEventLoopGroup(2, new DefaultThreadFactory("worker", true));
                channelClass = EpollSocketChannel.class;
            } else {
                workGroup = new NioEventLoopGroup(2, new DefaultThreadFactory("worker", true));
                channelClass = NioSocketChannel.class;
            }
            final Bootstrap bootstrap = new Bootstrap();
            // 地址复用
            bootstrap.option(ChannelOption.SO_REUSEADDR, true);
            // 发送缓冲区
            bootstrap.option(ChannelOption.SO_SNDBUF, 64 * 1024);
            // 接收缓冲区
            bootstrap.option(ChannelOption.SO_RCVBUF, 64 * 1024);
            // 低延时多交互次数
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            // 处理线程全满时，临时缓存的请求个数
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            final WriteBufferWaterMark write = new WriteBufferWaterMark(512 * 1024, 1024 * 1024);
            bootstrap.option(ChannelOption.WRITE_BUFFER_WATER_MARK, write);
            // 动态调整
            bootstrap.option(ChannelOption.RCVBUF_ALLOCATOR, AdaptiveRecvByteBufAllocator.DEFAULT);
            connect();
            bootstrap.group(workGroup).channel(channelClass).handler(
                    new WebSocketChannelInitializer(uri, host, port)
            );
            ChannelFuture channelFuture = bootstrap.connect(host, port).sync();
            log.info("market server start success");
            Channel channel = channelFuture.channel();
            ChannelUtil.put(CHANNEL_KEY, channel);
        } catch (Exception e) {
            log.error("netty-market start fail, exception ", e);
            if (workGroup != null) {
                workGroup.shutdownGracefully();
            }
        }
    }

    public void stop() {
        log.info("market server stop ...");
        if (workGroup != null) {
            workGroup.shutdownGracefully();
        }
        log.info("market server stop success");
    }

    public void connect() {
        String url = getUrl();
        uri = URI.create(url);
        String scheme = uri.getScheme() == null ? "http" : uri.getScheme();
        host = uri.getHost() == null ? "127.0.0.1" : uri.getHost();
        if (uri.getPort() == -1) {
            if ("http".equalsIgnoreCase(scheme) || "ws".equalsIgnoreCase(scheme)) {
                port = 80;
            } else if ("wss".equalsIgnoreCase(scheme)) {
                port = 443;
            } else {
                port = -1;
            }
        } else {
            port = uri.getPort();
        }
        if (!"ws".equalsIgnoreCase(scheme) && !"wss".equalsIgnoreCase(scheme)) {
            log.error("Only WS(S) is supported");
            throw new UnsupportedAddressTypeException();
        }
    }

    protected abstract String getUrl();

    public boolean isAlive() {
        return this.channel != null && this.channel.isActive();
    }

    public abstract void sendPing();

    public abstract void reConnect();
}
