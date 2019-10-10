package com.liyz.service.market.handler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/10/8 14:31
 */
@Slf4j
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    private URI uri;
    private String host;
    private int port;

    public WebSocketChannelInitializer(URI uri, String host, int port) {
        this.uri = uri;
        this.host = host;
        this.port = port;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        log.info("initChannel .... {}", socketChannel);
        final ChannelPipeline pipeline = socketChannel.pipeline();
        if (uri != null) {
            String scheme = uri.getScheme() == null ? "http" : uri.getScheme();
            final boolean ssl = "wss".equalsIgnoreCase(scheme);
            final SslContext sslCtx;
            if (ssl) {
                sslCtx = SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
                pipeline.addLast(sslCtx.newHandler(socketChannel.alloc(), host, port));
            }
        }
        //字符串解码器
        pipeline.addLast(new StringDecoder());
        //字符串编码器
        pipeline.addLast(new StringEncoder());
        //处理类
        pipeline.addLast(new MessagePushHandler());
    }
}
