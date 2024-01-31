package com.ji.jichat.chat.api.enums;

public enum ChatMessageTypeEnum {
    TEXT(101, "文字"),
    IMAGE(102, "图片"),
    VOICE(103, "语音"),
    VIDEO(104, "视频"),
    FILE(105, "文件"),
    RSA_PUBLIC_KEY(201, "RSA公钥"),
    END_TO_END_KEY(202, "端到端密钥"),
    END_TO_END_CLOSE(203, "关闭端到端加密"),
    RED_PACKET(301, "红包");

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

    public static ChatMessageTypeEnum getEnum(int code) {
        for (ChatMessageTypeEnum e : ChatMessageTypeEnum.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        throw new IllegalArgumentException(code + "找不到对应的Code");
    }
}


