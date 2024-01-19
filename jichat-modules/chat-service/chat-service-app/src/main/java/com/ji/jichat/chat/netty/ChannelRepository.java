package com.ji.jichat.chat.netty;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ChannelRepository
 *
 * @author lintengyue
 */
@Slf4j
public class ChannelRepository {

    private static final Map<String, Channel> channelCache = new ConcurrentHashMap<>(16);

    private ChannelRepository() {
    }

    public static Channel put(String clientIP, Channel channel) {
        log.debug("[{}]更新channel:[{}]", clientIP, channel.remoteAddress().toString());
        return channelCache.put(clientIP, channel);
    }

    public static Channel get(String clientIP) {
        return channelCache.get(clientIP);
    }

    public static Channel putIfAbsent(String clientIP, Channel newChannel) {
        Channel channel = channelCache.get(clientIP);
        if (Objects.isNull(channel)) {
            log.info("putIfAbsent clientIP=[{}]", clientIP);
            channel = put(clientIP, newChannel);
        }
        return channel;
    }

    public static void remove(String clientIP, Channel value) {
        if (channelCache.remove(clientIP, value)) {
            log.debug("[{}][{}]channel remove success", clientIP, value == null ? null : value.remoteAddress());
        }
    }

    public static int size() {
        return channelCache.size();
    }

    public static Map<String, Channel> getChannelCache() {
        return channelCache;
    }


}
