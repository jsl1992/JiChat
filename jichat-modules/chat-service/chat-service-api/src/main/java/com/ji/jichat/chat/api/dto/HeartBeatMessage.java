package com.ji.jichat.chat.api.dto;

import com.alibaba.fastjson2.JSON;
import com.ji.jichat.chat.api.enums.CommandCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author jisl on 2024/2/1 9:42
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class HeartBeatMessage extends Message {

    private String content;


    public static void encode(Message message) {
        System.out.println(JSON.toJSONString(message));
    }

    public static Message decode(Message message) {
        final String content = JSON.toJSONString(message);
        return JSON.parseObject(content, CommandCodeEnum.getClazz(message.getCode()));
    }

    public static void main(String[] args) {
        final HeartBeatMessage heartBeatMessage = new HeartBeatMessage();
        heartBeatMessage.setCode(CommandCodeEnum.HEARTBEAT.getCode());
        heartBeatMessage.setContent("123");
        HeartBeatMessage.encode(heartBeatMessage);
        final Message decode = HeartBeatMessage.decode(heartBeatMessage);
        System.out.println("HeartBeatMessage.decode(heartBeatMessage)" + JSON.toJSONString(decode));
    }
}
