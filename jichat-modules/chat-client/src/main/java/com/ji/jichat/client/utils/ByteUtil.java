package com.ji.jichat.client.utils;


import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ByteUtil {


    /**
     * @param byteOrg byte[]数组
     * @return int整形
     */
    public static int bytesToInt(byte[] byteOrg) {
        return bytesToInt(byteOrg, 0);
    }

    public static int bytesToInt(byte[] data, int offset) {
        return (data[offset] & 0xFF) << 24 |
                (data[offset + 1] & 0xFF) << 16 |
                (data[offset + 2] & 0xFF) << 8 |
                (data[offset + 3] & 0xFF);
    }

    public static String bytesToString(byte[] data, int offset, int length) {
        byte[] stringBytes = Arrays.copyOfRange(data, offset, offset + length);
        return new String(stringBytes, StandardCharsets.UTF_8).trim();
    }


    /**
     * int转成bytes
     * @param number 数字
     * @return  byte[]
     * @author jishenglong on 2023/8/16 10:39
     **/
    public static byte[] intToBytes(int number) {
        return new byte[]{
                (byte) (number >> 24),
                (byte) (number >> 16),
                (byte) (number >> 8),
                (byte) number
        };
    }


    public static String bytesToHexString(byte[] packageFooter) {
        StringBuilder sb = new StringBuilder(packageFooter.length * 2);
        for (byte b : packageFooter) {
            sb.append(String.format("%02X", b & 0xFF));
        }
        return sb.toString();
    }
}
