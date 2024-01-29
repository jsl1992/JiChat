package com.ji.jichat.chat.manager;

import com.ji.jichat.common.constants.CacheConstant;
import com.ji.jichat.common.util.MessageIdUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author jisl on 2024/1/20 13:51
 */
@Component
public class MessageIdGenerate {

    @Resource
    private RedisTemplate<String, Long> redisTemplate;

    public Long genMessageId(long userId, long userId2) {
        return redisTemplate.opsForValue().increment(CacheConstant.MESSAGE_ID + MessageIdUtil.getChannelKey(userId, userId2));
    }
}
