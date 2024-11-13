package com.ji.jichat.user.api.enums;

public enum SmsSceneEnum {
    MEMBER_LOGIN(1,  "会员用户 - 手机号登陆"),
    MEMBER_UPDATE_MOBILE(2,  "会员用户 - 修改手机"),
    MEMBER_UPDATE_PASSWORD(3, "会员用户 - 修改密码"),
    MEMBER_RESET_PASSWORD(4,  "会员用户 - 忘记密码"),

    ADMIN_MEMBER_LOGIN(21,  "后台用户 - 手机号登录");

    private final int code;
    private final String name;

    SmsSceneEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " (" + code + ")";
    }
}