package com.ji.jichat.chat.api.enums;

import java.util.Objects;

/**
 * 命令编码
 *
 * @author jisl on 2022/12/15 15:44
 **/
public enum CommandCodeEnum {
    NO_COMMAND(-1, "没有处理Processor"),
    HEARTBEAT(1001, "心跳"),
    LOGIN(1002, "登录"),
    PRIVATE_MESSAGE(2001, "私聊消息"),
    PRIVATE_MESSAGE_RECEIVE(2002, "私聊消息接收"),
    GROUP_MESSAGE(2102, "群消息"),


    /*  ================下发指令=================*/
    SYNC_TIME(5001, "同步时间"),
    ;


    /**
     * 类型
     */
    private final Integer code;
    /**
     * 类型名
     */
    private final String name;


    CommandCodeEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
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
}
