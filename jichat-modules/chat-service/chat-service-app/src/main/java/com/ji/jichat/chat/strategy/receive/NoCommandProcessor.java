package com.ji.jichat.chat.strategy.receive;


import com.ji.jichat.chat.strategy.CommandStrategy;
import com.ji.jichat.common.enums.CommandCodeEnum;
import com.ji.jichat.common.pojo.UpMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 没有执行Processor
 *
 * @author jishenglong on 2023/3/27 10:02
 **/
@Component
@Slf4j
public class NoCommandProcessor implements CommandStrategy {


    @Override
    public CommandCodeEnum getCommandCode() {
        return CommandCodeEnum.NO_COMMAND;
    }

    @Override
    public String execute(UpMessage message) {
        log.warn("当前执行:{}没有相关处理类,内容:{}", message.getCode(), message.getContent());
        return message.getContent();
    }
}
