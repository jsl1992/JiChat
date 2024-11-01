package com.ji.jichat.user.config;

import com.google.code.kaptcha.text.impl.DefaultTextCreator;

import java.util.Random;

/**
 * 生成验证码
 *
 * @author jisl on 2024/11/1 15:51
 **/
public class KaptchaTextCreator extends DefaultTextCreator {
    private static final String[] NUMBERS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

    @Override
    public String getText() {
        Random random = new Random();
        int x = random.nextInt(10);
        int y = random.nextInt(10);
        int result;
        StringBuilder sb = new StringBuilder();

        int operation = random.nextInt(3);
        switch (operation) {
            case 0: // Multiplication
                result = x * y;
                sb.append(NUMBERS[x]).append("*").append(NUMBERS[y]);
                break;
            case 1: // Division or Addition
                if (x != 0 && y % x == 0) { // Division only if divisible
                    result = y / x;
                    sb.append(NUMBERS[y]).append("/").append(NUMBERS[x]);
                } else {
                    result = x + y;
                    sb.append(NUMBERS[x]).append("+").append(NUMBERS[y]);
                }
                break;
            case 2: // Subtraction
                if (x >= y) {
                    result = x - y;
                    sb.append(NUMBERS[x]).append("-").append(NUMBERS[y]);
                } else {
                    result = y - x;
                    sb.append(NUMBERS[y]).append("-").append(NUMBERS[x]);
                }
                break;
            default: // Default to addition (should not happen)
                result = x + y;
                sb.append(NUMBERS[x]).append("+").append(NUMBERS[y]);
                break;
        }

        sb.append("=?").append(result);
        return sb.toString();
    }
}
