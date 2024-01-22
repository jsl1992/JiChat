package com.ji.jichat.common.enums;

/**
  *命令编码
  * @author jishenglong on 2022/12/15 15:44
  **/
public enum MessageTypeEnum {
    UP(1, "上传" ),
    DOWN(2, "下发" ),
    ;



    /**
     * 类型
     */
    private final Integer code;
    /**
     * 类型名
     */
    private final String name;



    MessageTypeEnum(Integer value, String name) {
        this.code = value;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }


    public static MessageTypeEnum getEnum(int code) {
        for (MessageTypeEnum e : values()) {
            if (e.code == code) {
                return e;
            }
        }
        throw new IllegalArgumentException(code + "找不到对应的Code");
    }

    public static MessageTypeEnum getEnumByName(String name) {
        for (MessageTypeEnum e : values()) {
            if (e.name.equals(name)) {
                return e;
            }
        }
        throw new IllegalArgumentException(name + "找不到对应的Name");
    }

    public static String getName(int code) {
        return getEnum(code).getName();
    }
}
