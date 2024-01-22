package com.ji.jichat.chat.strategy.receive;


import com.ji.jichat.chat.strategy.CommandStrategy;
import com.ji.jichat.common.enums.CommandCodeEnum;
import com.ji.jichat.common.pojo.UpMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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
