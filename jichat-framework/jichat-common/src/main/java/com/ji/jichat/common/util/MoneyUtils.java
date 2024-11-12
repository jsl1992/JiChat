package com.ji.jichat.common.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author guozhaobin
 * @date 2019/5/8 11:17
 */
public class MoneyUtils {

    public static Double cent2dollar(Number cent) {
        return cent == null ? 0.00 : cent.longValue() / 100D;
    }

    public static BigDecimal cent2dollarBD(Number cent) {
        return cent == null ? BigDecimal.ZERO : BigDecimal.valueOf(cent.longValue()).divide(BigDecimal.valueOf(100), 2, RoundingMode.DOWN);
    }

    public static Number dollar2centNumber(Double dollar) {
        return dollar == null ? 0 : BigDecimal.valueOf(dollar).multiply(BigDecimal.valueOf(100));
    }

    public static Number dollar2cent(Double dollar) {
        return dollar == null ? 0 : BigDecimal.valueOf(dollar).multiply(BigDecimal.valueOf(100));
    }

    public static Number dollar2cent(String dollar) {
        return dollar == null ? 0 : new BigDecimal(dollar).multiply(BigDecimal.valueOf(100));
    }

    public static BigDecimal dollar2centBD(Double dollar) {
        return dollar == null ? BigDecimal.ZERO : BigDecimal.valueOf(dollar).multiply(BigDecimal.valueOf(100));
    }

    public static Number dollar2centHalfUp(BigDecimal dollar) {
        return dollar == null ? 0 : dollar.multiply(BigDecimal.valueOf(100)).setScale(0, RoundingMode.HALF_UP);
    }

    public static void main(String[] args) {
        final BigDecimal bigDecimal = new BigDecimal("12.34");
        final BigDecimal bigDecimal2 = new BigDecimal("-0.3");
        final BigDecimal subtract = bigDecimal.subtract(bigDecimal2);
        System.out.println(subtract.toString());
    }

}
