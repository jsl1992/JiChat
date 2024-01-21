package com.ji.jichat.chat.manager;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.ji.jichat.common.constants.CacheConstant;
import com.ji.jichat.common.util.Base62Util;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author jisl on 2024/1/20 13:51
 */
@Component
public class MessageIdGenerate {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public long genMessageId(long userId, long userId2) {
        if (userId > userId2) {
//            保证较小的id排前面
            long temp = userId;
            userId = userId2;
            userId2 = temp;
        }
        String chatChannelId = Base62Util.encodeBase62(userId) + "_" + Base62Util.encodeBase62(userId2);
        String key = CacheConstant.MESSAGE_ID + chatChannelId;
        final long messageId = redisTemplate.opsForValue().increment(CacheConstant.MESSAGE_ID + key);
        return messageId;
    }
}
