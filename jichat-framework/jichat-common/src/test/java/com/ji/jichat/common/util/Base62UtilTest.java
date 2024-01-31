package com.ji.jichat.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class Base62UtilTest {


    @Test
    void testEncodeAndDecodeBase62() {
        long[] testNumbers = {123, 456, 789, 9876543210L, Long.MAX_VALUE};

        for (long number : testNumbers) {
            String base62Encoded = Base62Util.encodeBase62(number);
            long decodedNumber = Base62Util.decodeBase62(base62Encoded);
            Assertions.assertEquals(number, decodedNumber, "Mismatch for number: " + number);
        }
    }


}
