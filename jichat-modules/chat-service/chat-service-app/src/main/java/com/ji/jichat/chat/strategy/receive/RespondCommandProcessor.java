package com.ji.jichat.chat.strategy.receive;


import com.ji.jichat.chat.dto.Message;
import com.ji.jichat.chat.enums.CommandCodeEnum;
import com.ji.jichat.chat.strategy.CommandStrategy;
import com.ji.jichat.chat.utils.ByteUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * 指令回复Processor
 *
 * @author jishenglong on 2023/3/27 10:02
 **/
@Component
@Slf4j
public class RespondCommandProcessor implements CommandStrategy {


    @Override
    public CommandCodeEnum getCommandCode() {
        return CommandCodeEnum.RESPOND_COMMAND;
    }

    @Override
    public byte[] execute(Message message) {
        final byte[] content = message.getContent();
//        设备识别编码
        int offSet = 1;
        final String deviceSn = ByteUtil.bytesToString(content, offSet, 16);
        offSet += 16;
        int year = ByteUtil.bytesToInt(content, offSet);
        offSet += 2;
        final int month = ByteUtil.byteToInt(content[offSet++]);
        final int day = ByteUtil.byteToInt(content[offSet++]);
        final int hour = ByteUtil.byteToInt(content[offSet++]);
        final int minute = ByteUtil.byteToInt(content[offSet++]);
        final int second = ByteUtil.byteToInt(content[offSet++]);
        final LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute, second);
        log.info("收到指令回复code:{}:ip:{},设备时间:{},deviceSn:{}", message.getCode(), message.getClientIp(), dateTime,deviceSn);
        return message.content;
    }
}
