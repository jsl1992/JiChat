package com.ji.jichat.client.utils;


import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Map;

/**
 * Ji签名工具类
 *
 * @author jisl on 2023/6/8 14:03
 */
public class JiDigitUtil {


    private static final String HMAC_SHA_256_ALGORITHM = "HmacSHA256";

    public static final String RSA_ALGORITHM = "RSA";
    private static final String RSA_SHA_ALGORITHM = "SHA256WithRSA";

    private static final String ECC_ALGORITHM = "EC";
    private static final String ECDSA_SHA_ALGORITHM = "SHA384withECDSA";

    public static final String AES_ALGORITHM = "AES";

    private static final String AES_CIPHER_TRANSFORMATION = "AES/CBC/PKCS5Padding";

    public static final String AUTH_STRING_PREFIX = "AuthStringPrefix";

    public static final String AUTHORIZATION = "Authorization";

    public static final String ENCRYPT_VERSION_FIELD = "jiEncryptVersion";
    public static final String CIPHER_TEXT_FIELD = "ciphertext";


    /**
     * * HMAC（Hash-based Message Authentication Code，散列消息认证码）是一种使用密码散列函数，同时结合一个加密密钥，通过特别计算方式之后产生的消息认证码（MAC）。它可以用来保证数据的完整性，同时可以用来作某个消息的身份验证。
     * * JWT 默认的签名算法 HMAC SHA256
     *
     * @param secretKey secretKey
     * @param data      内容
     * @return java.lang.String
     * @author jisl on 2023/12/22 19:09
     **/
    private static String hmacSha256(String secretKey, String data) {
        try {
            // 2. 初始化Mac对象
            Mac hmacSha256 = Mac.getInstance(HMAC_SHA_256_ALGORITHM);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), HMAC_SHA_256_ALGORITHM);
            hmacSha256.init(secretKeySpec);
            // 3. 计算HMAC
            byte[] hmacBytes = hmacSha256.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hmacBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String hmacSha256(String secretKey, String authStringPrefix, Map<String, Object> data) {
//        生成signingKey
        String signingKey = hmacSha256(secretKey, authStringPrefix);
//        内容生成摘要
        return hmacSha256(signingKey, getBodyDataStr(data));
    }

    public static String getBodyDataStr(Map<String, Object> data) {
        String jsonString = JSON.toJSONString(data, JSONWriter.Feature.SortMapEntriesByKeys);
        System.out.println("待加密数据:" + jsonString);
        return jsonString;
    }

    /**
     * 私钥签名
     *
     * @param data             内容
     * @param privateKeyBase64 私钥
     * @param algorithm        算法
     * @param signatureName    签名算法
     * @return java.lang.String
     * @author jisl on 2023/12/23 14:07
     **/
    private static String sign(String data, String privateKeyBase64, String algorithm, String signatureName) {
        byte[] privateKeyBytes = Base64.getDecoder().decode(privateKeyBase64);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

            Signature signature = Signature.getInstance(signatureName);
            signature.initSign(privateKey);
            signature.update(data.getBytes(StandardCharsets.UTF_8));
            byte[] signatureBytes = signature.sign();
            return Base64.getEncoder().encodeToString(signatureBytes);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

    }


    /**
     * RSA私钥签名
     *
     * @param authStringPrefix 签名关键信息前缀
     * @param bodyData         内容
     * @param privateKeyBase64 私钥
     * @return java.lang.String
     * @author jisl on 2023/12/22 19:41
     **/
    public static String signRsa(String authStringPrefix, Map<String, Object> bodyData, String privateKeyBase64) {
//        data=authStringPrefix+bodyData
        return sign(authStringPrefix + getBodyDataStr(bodyData), privateKeyBase64, RSA_ALGORITHM, RSA_SHA_ALGORITHM);
    }


    /**
     * ECDSA私钥签名
     *
     * @param authStringPrefix 签名关键信息前缀
     * @param bodyData         内容
     * @param privateKeyBase64 私钥
     * @return java.lang.String
     * @author jisl on 2023/12/22 19:41
     **/
    public static String signEcdsa(String authStringPrefix, Map<String, Object> bodyData, String privateKeyBase64) {
//        data=authStringPrefix+bodyData
        return sign(authStringPrefix + getBodyDataStr(bodyData), privateKeyBase64, ECC_ALGORITHM, ECDSA_SHA_ALGORITHM);
    }

    /**
     * 使用公钥进行验证签名
     *
     * @param data            内容
     * @param signature       签名
     * @param publicKeyBase64 公钥
     * @param algorithm       算法
     * @param signatureName   签名类型
     * @return boolean
     * @author jisl on 2023/12/23 14:05
     **/
    private static boolean verify(String data, String signature, String publicKeyBase64, String algorithm, String signatureName) {
        byte[] publicKeyBytes = Base64.getDecoder().decode(publicKeyBase64);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            Signature signatureVerifier = Signature.getInstance(signatureName);
            signatureVerifier.initVerify(publicKey);
            signatureVerifier.update(data.getBytes());
            byte[] signatureBytes = Base64.getDecoder().decode(signature);
            return signatureVerifier.verify(signatureBytes);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | SignatureException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * RSA公钥验证签名
     *
     * @param authStringPrefix 签名关键信息前缀
     * @param bodyData         bodyData
     * @param signature        签名
     * @param publicKeyBase64  公钥
     * @return boolean
     * @author jisl on 2023/12/22 19:43
     **/
    public static boolean verifyRsa(String authStringPrefix, Map<String, Object> bodyData, String signature, String publicKeyBase64) {
//        data=authStringPrefix+bodyData
        return verify(authStringPrefix + getBodyDataStr(bodyData), signature, publicKeyBase64, RSA_ALGORITHM, RSA_SHA_ALGORITHM);
    }

    /**
     * ECDSA公钥验证签名
     *
     * @param authStringPrefix 签名关键信息前缀
     * @param bodyData         bodyData
     * @param signature        签名
     * @param publicKeyBase64  公钥
     * @return boolean
     * @author jisl on 2023/12/22 19:43
     **/
    public static boolean verifyEcdsa(String authStringPrefix, Map<String, Object> bodyData, String signature, String publicKeyBase64) {
//        data=authStringPrefix+bodyData
        return verify(authStringPrefix + getBodyDataStr(bodyData), signature, publicKeyBase64, ECC_ALGORITHM, ECDSA_SHA_ALGORITHM);
    }


    /**
     * AES 加密操作
     *
     * @param plaintext       明文
     * @param secretKeyBase64 密钥
     * @param ivStr           偏移量  偏移量通常称为初始化向量（Initialization Vector，IV）。初始化向量是在使用某些分组密码工作模式时引入的一种附加输入，用于增加加密的随机性和安全性。随密文一起传输。（随机数）
     * @return java.lang.String 密文
     * @author jisl on 2022/2/16 13:53
     **/
    public static String encryptAes(String plaintext, String secretKeyBase64, String ivStr) {
        try {
            // 创建密码器 算法/工作模式/填充模式(algorithm/mode/padding)
            Cipher cipher = Cipher.getInstance(AES_CIPHER_TRANSFORMATION);
            byte[] byteContent = plaintext.getBytes(StandardCharsets.UTF_8);
            // 初始化为加密模式的密码器
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(Base64.getDecoder().decode(secretKeyBase64), AES_ALGORITHM), new IvParameterSpec(ivStr.getBytes()));
            // 加密
            byte[] result = cipher.doFinal(byteContent);
            //通过Base64转码返回
            return Base64.getEncoder().encodeToString(result);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

    }


    /**
     * AES 解密操作
     *
     * @param ciphertext      密文
     * @param secretKeyBase64 密钥
     * @param ivStr           iv
     * @return java.lang.String
     * @author jisl on 2023/12/23 14:03
     **/
    public static String decryptAes(String ciphertext, String secretKeyBase64, String ivStr) {
        try {
            //实例化
            Cipher cipher = Cipher.getInstance(AES_CIPHER_TRANSFORMATION);
            //使用密钥初始化，设置为解密模式
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(Base64.getDecoder().decode(secretKeyBase64), AES_ALGORITHM), new IvParameterSpec(ivStr.getBytes()));
            //执行操作
            byte[] result = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
            return new String(result, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            throw new RuntimeException(ex.getMessage());
        }

    }

    /**
     * RSA公钥加密
     */
    public static String encryptRsa(String str, String publicKeyBase64) {
        try {
            //base64编码的公钥
            byte[] decoded = Base64.getDecoder().decode(publicKeyBase64);
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(RSA_ALGORITHM).generatePublic(new X509EncodedKeySpec(decoded));
            //RSA加密
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            return Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * RSA私钥解密
     */
    public static String decryptRsa(String str, String privateKeyBase64) {
        try {
            //64位解码加密后的字符串
            byte[] inputByte = Base64.getDecoder().decode(str.getBytes(StandardCharsets.UTF_8));
            //base64编码的私钥
            byte[] decoded = Base64.getDecoder().decode(privateKeyBase64);
            RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance(RSA_ALGORITHM).generatePrivate(new PKCS8EncodedKeySpec(decoded));
            //RSA解密
            Cipher cipher = Cipher.getInstance(RSA_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, priKey);
            return new String(cipher.doFinal(inputByte));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    /**
     * 生成Appkey (前两位组织代码)
     *
     * @return java.lang.String
     * @author jisl on 2023/12/22 19:57
     **/
    private static String generateAppKey() {
        return "ji" + createNonce(16);
    }


    public static String genSecretKey(String algorithmName) {
        try {
            // 创建一个安全随机数生成器
            KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithmName);
            // 初始化密钥生成器，使用安全随机数生成器
            SecretKey secretKey = keyGenerator.generateKey();
            // 打印生成的密钥
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

    public static String[] genKeyPair(String algorithm) {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen;
        try {
            keyPairGen = KeyPairGenerator.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到公钥
        PublicKey publicKey = keyPair.getPublic();
        // 得到私钥
        PrivateKey privateKey = keyPair.getPrivate();
        String publicKeyString = Base64.getEncoder().encodeToString(publicKey.getEncoded());
        String privateKeyString = Base64.getEncoder().encodeToString(privateKey.getEncoded());
        return new String[]{publicKeyString, privateKeyString};
    }


    private static final char[] HEX_ARRAY = "0123456789abcdef".toCharArray();

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int v = bytes[i] & 0xFF;
            hexChars[i * 2] = HEX_ARRAY[v >>> 4];
            hexChars[i * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    private static final char[] SYMBOLS =
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 使用SecureRandom生成随机串
     *
     * @param length 随机串长度
     * @return nonce 随机串
     */
    public static String createNonce(int length) {
        char[] buf = new char[length];
        for (int i = 0; i < length; ++i) {
            buf[i] = SYMBOLS[RANDOM.nextInt(SYMBOLS.length)];
        }
        return new String(buf);
    }

    private static void genJiKey() {
        System.out.println("AppKey:" + generateAppKey());
        System.out.println("AppSecret:" + genSecretKey(HMAC_SHA_256_ALGORITHM));
        System.out.println("AESSecretKey:" + genSecretKey(AES_ALGORITHM));
        String[] keyPair = genKeyPair(RSA_ALGORITHM);
        System.out.println("publicKey:" + keyPair[0]);
        System.out.println("privateKey:" + keyPair[1]);

        String[] eccKeyPair = genKeyPair(ECC_ALGORITHM);
        System.out.println("ECC_publicKey:" + eccKeyPair[0]);
        System.out.println("ECC_privateKey:" + eccKeyPair[1]);
    }

    public static void main(String[] args) {
        genJiKey();
        createNonce(32);
        System.out.println(createNonce(32));
    }

}
