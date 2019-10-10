package com.liyz.service.market.util;

import com.google.common.collect.Lists;
import io.netty.channel.Channel;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 注释:
 *
 * @author liyangzhen
 * @version 1.0.0
 * @date 2019/10/10 17:38
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ChannelUtil {

    private static volatile Map<String, List<Channel>> channelMap = new HashMap<>();

    public static synchronized void put(String key, Channel channel) {
        List<Channel> list;
        if (channelMap.containsKey(key)) {
            list = channelMap.get(key);
        } else {
            list = Lists.newArrayList();
        }
        list.add(channel);
    }

    public static Map<String, List<Channel>> getChannelMap() {
        return channelMap;
    }
}
