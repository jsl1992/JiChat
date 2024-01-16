package com.ji.jichat.chat.enums;

/**
 * 命令编码
 *
 * @author jishenglong on 2022/12/15 15:44
 **/
public enum CommandCodeEnum {
    NO_COMMAND("NO_COMMAND", "没有处理Processor"),
    HEARTBEAT("02", "心跳"),
    DYNAMICS_TRAFFIC_DATA("01", "动态交通数据"),
    RESPOND_COMMAND("08", "客户端应答下发指令上报"),

    /*  ================下发指令=================*/
    SYNC_TIME("05", "同步时间"),
    ;


    /**
     * 类型
     */
    private final String code;
    /**
     * 类型名
     */
    private final String name;



    CommandCodeEnum(String value, String name) {
        this.code = value;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }


    public static CommandCodeEnum getEnum(String code) {
        for (CommandCodeEnum e : values()) {
            if (e.code.equals(code)) {
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

    public static String getName(String code) {
        return getEnum(code).getName();
    }
}
