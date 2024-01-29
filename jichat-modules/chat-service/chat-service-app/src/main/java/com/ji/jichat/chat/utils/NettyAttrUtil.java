package com.ji.jichat.chat.utils;

import io.netty.channel.Channel;
import io.netty.util.Attribute;
import io.netty.util.AttributeKey;

import java.util.Objects;

/**
 * Function:
 *
 * @author crossoverJie
 * Date: 2019/1/9 00:57
 * @since JDK 1.8
 */
public class NettyAttrUtil {

    private static final AttributeKey<String> ATTR_KEY_READER_TIME = AttributeKey.valueOf("readerTime");


    public static void updateReaderTime(Channel channel, Long time) {
        channel.attr(ATTR_KEY_READER_TIME).set(time.toString());
    }

    public static Long getReaderTime(Channel channel) {
        String value = getAttribute(channel);
        if (Objects.isNull(value)) {
            return 0L;
        }
        return Long.valueOf(value);
    }


    private static String getAttribute(Channel channel) {
        Attribute<String> attr = channel.attr(NettyAttrUtil.ATTR_KEY_READER_TIME);
        return attr.get();
    }
}
