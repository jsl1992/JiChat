package com.ji.jichat.excel.util.dto;

/**
 * 公用enum
 *
 * @author jishenglong on 2019/11/19 18:42
 **/
public enum TestCommonEnum {

    BACKEND_ISSUANCE(1, "后台发放"),
    SELF_PURCHASE(2, "自助购买"),
    CLEAR_VOUCHER(3, "清除发券");

    private final Integer code;
    private final String name;

    TestCommonEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public static String getName(Integer index) {
        for (TestCommonEnum value : TestCommonEnum.values()) {
            if (value.getCode().equals(index)) {
                return value.getName();
            }
        }
        return null;
    }

    public static Integer getCode(String name) {
        for (TestCommonEnum value : TestCommonEnum.values()) {
            if (value.getName().equals(name)) {
                return value.getCode();
            }
        }
        return null;
    }

    public static TestCommonEnum getEnum(Integer index) {
        for (TestCommonEnum value : TestCommonEnum.values()) {
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
