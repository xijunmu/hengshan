package com.hengshan.common.utils;

import org.springframework.util.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class AESUtil {

    private static final String KEY_ALGORITHM = "AES";
    private static final String DEFAULT_CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";//默认加密算法
    private static final String KEY = "hengshan.com";

    /**
     * 生成加密秘钥
     *
     * @return AES密钥
     */
    private static SecretKeySpec getSecretKey() throws Exception {
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
        SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
        random.setSeed(KEY.getBytes(StandardCharsets.UTF_8));
        kg.init(128, random);//AES要求密钥长度为 128,192或者256位
        SecretKey secretKey = kg.generateKey();//生成一个密钥
        return new SecretKeySpec(secretKey.getEncoded(), KEY_ALGORITHM);// 转换为AES专用密钥
    }

    /**
     * AES 加密操作
     *
     * @param content 待加密内容
     * @return 返回加密数据
     */
    public static String encrypt(String content) {
        try {
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);// 创建密码器
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey());// 初始化为加密模式的密码器
            byte[] result = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));// 加密
            //return Base64.encodeBase64String(result);//返回Base64转码
            return byte2hex(result); //返回16进制编码
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * AES 解密操作
     *
     * @param content 待解密内容
     * @return 返回解密数据
     */
    public static String decrypt(String content) {
        try {
            if (!StringUtils.hasText(content)) {
                return null;
            }
            Cipher cipher = Cipher.getInstance(DEFAULT_CIPHER_ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey());
            //byte[] encrypted = Base64.decodeBase64(content);
            byte[] encrypted = hex2byte(content);
            byte[] result = cipher.doFinal(encrypted);
            return new String(result, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] hex2byte(String strhex) {
        if (strhex == null) {
            return null;
        }
        int l = strhex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2), 16);
        }
        return b;
    }

    public static String byte2hex(byte[] b) {
        StringBuilder hs = new StringBuilder();
        String stmp;
        for (byte value : b) {
            stmp = (Integer.toHexString(value & 0XFF));
            if (stmp.length() == 1) {
                hs.append("0").append(stmp);
            } else {
                hs.append(stmp);
            }
        }
        return hs.toString().toUpperCase();
    }

    public static void main(String[] args) throws Exception {
        String origin = "123456";
        String encrypt = AESUtil.encrypt(origin);
        String decrypt = AESUtil.decrypt(encrypt);
        System.out.println(origin);
        System.out.println(encrypt);
        System.out.println(decrypt);
    }
}
