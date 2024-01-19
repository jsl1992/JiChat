package com.ji.jichat.chat.utils;

public class ByteUtil {


    /**
     * @param byteOrg byte[]数组
     * @return int整形
     */
    public static int bytesToInt(byte[] byteOrg) {
        return bytesToInt(byteOrg, 0);
    }

    public static int bytesToInt(byte[] data, int offset) {
//        小端法 0x1234，存储顺序0x34,0x12
        return (data[offset] & 0xFF) | (data[offset + 1] & 0xFF) << 8;
    }

    public static int byteToInt(byte b) {
//        小端法 0x1234，存储顺序0x34,0x12
        return b & 0xFF;
    }

    /**
     * 将byte转成成ASCII编码
     *
     * @param data   数据
     * @param offset 起始位置
     * @param length 转换的长度
     * @return java.lang.String
     * @author jisl on 2023/10/16 9:48
     **/
    public static String bytesToString(byte[] data, int offset, int length) {
        final StringBuilder sb = new StringBuilder();
        for (int i = offset; i < offset + length; i++) {
            sb.append((char) data[i]);
        }
        return sb.toString();
    }


    /**
     * int转成bytes（小端法2字节）
     *
     * @param number 数字
     * @return byte[]
     * @author jishenglong on 2023/8/16 10:39
     **/
    public static byte[] intToBytes(int number) {
        return new byte[]{(byte) number, (byte) (number >> 8)};
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            sb.append(byteToHexString(b)).append(" ");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }


    public static String byteToHexString(byte b) {
        return String.format("%02X", b & 0xFF);
    }

    public static byte hexStringToByte(String str) {
        int value = Integer.decode(str); // 将字符串解析为整数
        return (byte) value;
    }

    public static int fillBytes(byte[] dest, byte[] src, int from) {
        System.arraycopy(src, 0, dest, from, src.length);
        return from + src.length;
    }

    public static int fillByte(byte[] a, byte b, int from) {
        a[from] = b;
        return from + 1;
    }


    public static byte[] mergeBytes(byte[] a, byte[] b) {
        final byte[] bytes = new byte[a.length + b.length];
        System.arraycopy(a, 0, bytes, 0, a.length);
        System.arraycopy(b, 0, bytes, a.length, b.length);
        return bytes;
    }

    public static void main(String[] args) {
        final byte[] bytes = new byte[]{0x30, 0x31};
        final String s = ByteUtil.bytesToString(bytes, 0, bytes.length);
        System.out.println(s);
        final byte aByte = ByteUtil.hexStringToByte("0x23");
        System.out.println(ByteUtil.bytesToHexString(new byte[]{aByte}));
    }
}
