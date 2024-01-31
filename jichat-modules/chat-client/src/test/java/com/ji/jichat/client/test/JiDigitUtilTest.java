package com.ji.jichat.client.test;

import com.ji.jichat.client.utils.JiDigitUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JiDigitUtilTest {


    @Test
    public void testRSAEncryptDecrypt() {
        try {
            // 生成RSA密钥对
            final String[] keyPair = JiDigitUtil.genKeyPair(JiDigitUtil.RSA_ALGORITHM);
            String publicKeyBase64 = keyPair[0];
            String privateKeyBase64 = keyPair[1];
            // 待加密的字符串
            String originalData = "Hello, RSA!";
            // 使用公钥加密
            String encryptedData = JiDigitUtil.encryptRsa(originalData, publicKeyBase64);
            // 使用私钥解密
            String decryptedData = JiDigitUtil.decryptRsa(encryptedData, privateKeyBase64);
            // 验证加解密结果
            assertEquals(originalData, decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testAESEncryptDecrypt() {
        try {
            // 生成RSA密钥对
            final String secretKey = JiDigitUtil.genSecretKey(JiDigitUtil.AES_ALGORITHM);
//            final String nonce = JiDigitUtil.createNonce(16);
            final String nonce = RandomStringUtils.randomAlphanumeric(16);
            // 待加密的字符串
            String originalData = "Hello, AES!";
            // 使用公钥加密
            String encryptedData = JiDigitUtil.encryptAes(originalData, secretKey,nonce);
            // 使用私钥解密
            String decryptedData = JiDigitUtil.decryptAes(encryptedData, secretKey,nonce);
            // 验证加解密结果
            assertEquals(originalData, decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
