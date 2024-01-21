package com.ji.jichat.chat.strategy.receive;


import com.ji.jichat.chat.enums.CommandCodeEnum;
import com.ji.jichat.chat.strategy.CommandStrategy;
import com.ji.jichat.chat.utils.ByteUtil;
import com.ji.jichat.common.pojo.UpMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * 指令回复Processor
 *
 * @author jishenglong on 2023/3/27 10:02
 **/
@Component
@Slf4j
public class MessageProcessor implements CommandStrategy {


    @Override
    public CommandCodeEnum getCommandCode() {
        return CommandCodeEnum.MESSAGE;
    }

    @Override
    public String execute(UpMessage message) {
        final String content = message.getContent();

        return message.getContent();
    }
}
