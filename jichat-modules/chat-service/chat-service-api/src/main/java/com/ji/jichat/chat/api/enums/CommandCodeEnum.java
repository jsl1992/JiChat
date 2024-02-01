package com.ji.jichat.chat.api.enums;

import com.ji.jichat.chat.api.dto.ChatSendMessage;
import com.ji.jichat.chat.api.dto.HeartBeatMessage;
import com.ji.jichat.chat.api.dto.LoginMessage;

import java.util.Objects;

/**
 * 命令编码
 *
 * @author jisl on 2022/12/15 15:44
 **/
public enum CommandCodeEnum {
    NO_COMMAND(-1, "没有处理Processor",HeartBeatMessage.class),
    HEARTBEAT(1001, "心跳", HeartBeatMessage.class),
    LOGIN(1002, "登录", LoginMessage.class),
    PRIVATE_MESSAGE(2001, "私聊消息", ChatSendMessage.class),
    PRIVATE_MESSAGE_RECEIVE(2002, "私聊消息接收", ChatSendMessage.class);

    /**
     * 类型
     */
    private final Integer code;
    /**
     * 类型名
     */
    private final String name;

    private final Class clazz ;


    CommandCodeEnum(Integer code, String name, Class clazz) {
        this.code = code;
        this.name = name;
        this.clazz = clazz;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Class getClazz() {
        return clazz;
    }


    public static CommandCodeEnum getEnum(Integer code) {
        for (CommandCodeEnum e : values()) {
            if (Objects.equals(e.code, code)) {
                return e;
            }
        }
        throw new IllegalArgumentException(code + "找不到对应的Code");
    }

    public static CommandCodeEnum getEnumByName(String name) {
        for (CommandCodeEnum e : values()) {
            if (e.name.equals(name)) {
                return e;
            }
        }
        throw new IllegalArgumentException(name + "找不到对应的Name");
    }

    public static String getName(Integer code) {
        return getEnum(code).getName();
    }

    public static <T> Class<T> getClazz(Integer code) {
        return getEnum(code).getClazz();
    }
}
