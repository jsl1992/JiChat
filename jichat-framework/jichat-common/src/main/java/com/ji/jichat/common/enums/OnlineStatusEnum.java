package com.ji.jichat.common.enums;

/**
 * 在线状态 枚举
 *
 * @author jisl on 2024/1/29 10:38
 **/
public enum OnlineStatusEnum {
    OFFLINE(0, "离线"),
    ONLINE(1, "在线");

    private final int code;
    private final String name;

    OnlineStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
