package com.ji.jichat.excel.util;

/**
 * 公用enum
 *
 * @author jishenglong on 2019/11/19 18:42
 **/
public enum CommonEnum {

    DISABLE(0, "禁用"),
    ENABLE(1, "启用"),
    DELETE(2, "删除");

    private final Integer code;
    private final String name;

    CommonEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getName(Integer index) {
        for (CommonEnum value : CommonEnum.values()) {
            if (value.getCode().equals(index)) {
                return value.getName();
            }
        }
        return null;
    }

    public static Integer getCode(String name) {
        for (CommonEnum value : CommonEnum.values()) {
            if (value.getName().equals(name)) {
                return value.getCode();
            }
        }
        return null;
    }

    public static CommonEnum getEnum(Integer index) {
        for (CommonEnum value : CommonEnum.values()) {
            if (value.getCode().equals(index)) {
                return value;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
