package com.ji.jichat.chat.test;

import static org.junit.jupiter.api.Assertions.*;

import com.ji.jichat.chat.utils.ByteUtil;
import org.junit.jupiter.api.Test;

class ByteUtilTest {

    @Test
    void testBytesToInt() {
        byte[] bytes = {0x01, 0x02, 0x03, 0x04};
        int result = ByteUtil.bytesToInt(bytes);
        assertEquals(16909060, result);
    }

    @Test
    void testBytesToString() {
        byte[] bytes = "Hello".getBytes();
        String result = ByteUtil.bytesToString(bytes, 0, bytes.length);
        assertEquals("Hello", result);
    }

    @Test
    void testIntToBytes() {
        int number = 16909060;
        byte[] result = ByteUtil.intToBytes(number);
        byte[] expected = {0x01, 0x02, 0x03, 0x04};
        assertArrayEquals(expected, result);
    }

    @Test
    void testBytesToHexString() {
        byte[] bytes = {0x01, 0x02, 0x0A, 0x0F};
        String result = ByteUtil.bytesToHexString(bytes);
        assertEquals("01020A0F", result);
    }
}
