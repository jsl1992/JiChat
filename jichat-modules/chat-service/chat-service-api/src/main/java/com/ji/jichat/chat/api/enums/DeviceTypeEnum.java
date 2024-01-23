package com.ji.jichat.chat.api.enums;

public enum DeviceTypeEnum {
    MOBILE(1, "手机"),
    COMPUTER(2, "电脑"),
    TABLET(3, "平板");

    private final int code;
    private final String name;

    DeviceTypeEnum(int code, String name) {
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
