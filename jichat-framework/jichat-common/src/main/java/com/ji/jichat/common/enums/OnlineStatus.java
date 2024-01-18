package com.ji.jichat.common.enums;

public enum OnlineStatus {
    OFFLINE(0, "离线"),
    ONLINE(1, "在线");

    private final int code;
    private final String name;

    OnlineStatus(int code, String name) {
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
