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

    private static final Map<String, Channel> channelCache = new ConcurrentHashMap<>(16);

    private ChannelRepository() {
    }

    public static Channel put(String userKey, Channel channel) {
        log.debug("[{}]更新channel:[{}]", userKey, channel.remoteAddress().toString());
        return channelCache.put(userKey, channel);
    }

    public static Channel get(String userKey) {
        return channelCache.get(userKey);
    }

    public static Channel putIfAbsent(String userKey, Channel newChannel) {
        Channel channel = channelCache.get(userKey);
        if (Objects.isNull(channel)) {
            log.info("putIfAbsent userKey=[{}]", userKey);
            channel = put(userKey, newChannel);
        }
        return channel;
    }

    public static void remove(String userKey, Channel value) {
        if (channelCache.remove(userKey, value)) {
            log.debug("[{}][{}]channel remove success", userKey, value == null ? null : value.remoteAddress());
        }
    }

    public static int size() {
        return channelCache.size();
    }

    public static Map<String, Channel> getChannelCache() {
        return channelCache;
    }


}
