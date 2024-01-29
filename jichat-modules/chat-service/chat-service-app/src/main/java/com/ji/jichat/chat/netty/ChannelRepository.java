package com.ji.jichat.chat.netty;

import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ChannelRepository
 *
 * @author jisl
 */
@Slf4j
public class ChannelRepository {

    private static final Map<String, Channel> CHANNEL_CACHE = new ConcurrentHashMap<>(16);

    private ChannelRepository() {
    }

    public static Channel put(String userKey, Channel channel) {
        log.debug("[{}]更新channel:[{}]", userKey, channel.remoteAddress().toString());
        return CHANNEL_CACHE.put(userKey, channel);
    }

    public static Channel get(String userKey) {
        return CHANNEL_CACHE.get(userKey);
    }

    public static Channel putIfAbsent(String userKey, Channel newChannel) {
        Channel channel = CHANNEL_CACHE.get(userKey);
        if (Objects.isNull(channel)) {
            log.info("putIfAbsent userKey=[{}]", userKey);
            channel = put(userKey, newChannel);
        }
        return channel;
    }

    public static void remove(String userKey) {
        CHANNEL_CACHE.remove(userKey);
    }

    public static int size() {
        return CHANNEL_CACHE.size();
    }

    public static String getUserKey(Channel channel) {
        for (Map.Entry<String, Channel> entry : CHANNEL_CACHE.entrySet()) {
            if (entry.getValue().equals(channel)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static Map<String, Channel> getChannelCache() {
        return CHANNEL_CACHE;
    }


}
