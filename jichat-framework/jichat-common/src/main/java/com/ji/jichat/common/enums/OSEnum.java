package com.ji.jichat.common.enums;

/**
 * 操作系统枚举
 *
 * @author jisl on 2024/1/29 10:39
 **/
public enum OSEnum {
    ANDROID(101, "Android"),
    IOS(102, "iOS"),
    WINDOWS(201, "Windows"),
    LINUX(202, "Linux"),
    IPAD(301, "iPad"),
    ANDROID_TABLET(302, "Android Tablet");

    private final int code;
    private final String name;

    OSEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    // 根据 code 获取对应的 OSType 枚举值
    public static OSEnum getByCode(int code) {
        for (OSEnum osEnum : values()) {
            if (osEnum.getCode() == code) {
                return osEnum;
            }
        }
        throw new IllegalArgumentException("Invalid OS code: " + code);
    }

}
