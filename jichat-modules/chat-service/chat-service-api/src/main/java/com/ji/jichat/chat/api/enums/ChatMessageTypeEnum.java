package com.ji.jichat.chat.api.enums;

public enum ChatMessageTypeEnum {
    TEXT(1, "文字"),
    IMAGE(2, "图片"),
    VOICE(3, "语音"),
    VIDEO(4, "视频"),
    FILE(5, "文件");

    private final int code;
    private final String name;

    ChatMessageTypeEnum(int code, String name) {
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


