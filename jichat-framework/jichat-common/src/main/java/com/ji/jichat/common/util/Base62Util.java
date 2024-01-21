package com.ji.jichat.common.util;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;

/**
  * 用大小写和数字62个生成短链
  * @author jisl on 2023/12/28 16:23
  **/
public class Base62Util {
    //将用大小写和数字打乱，这样增加识别难度
    private static final String CHARS = "Hgcw5ObvZIdmtrXkTL2eM8YRfqWujEP34s1oKzFUJAx6y0lG7DaC9NQnSiVpBhXqyLJR";

    public static String encodeBase62(long number) {
        StringBuilder base62 = new StringBuilder();

        while (number > 0) {
            int remainder = (int) (number % 62);
            base62.insert(0, CHARS.charAt(remainder));
            number /= 62;
        }

        return base62.length() == 0 ? "0" : base62.toString();
    }

    public static long decodeBase62(String base62String) {
        long result = 0;
        int base = 62;

        for (int i = 0; i < base62String.length(); i++) {
            char c = base62String.charAt(i);
            int charValue = CHARS.indexOf(c);
            result = result * base + charValue;
        }

        return result;
    }

    public static void main(String[] args) {

        // 生成10个ID进行演示
        for (int i = 0; i < 10; i++) {
//            每次使用不同的 workerId，这样生成的id都不一样，即使一起生成
            long id = IdUtil.getSnowflake(RandomUtil.randomInt(32),1).nextId();
            String base62Encoded = encodeBase62(id);
            System.out.println(String.format("ID=:%s,base62Encoded=%s,decodeBase62=%s",id,base62Encoded, Base62Util.decodeBase62(base62Encoded)) );

        }
    }
}
