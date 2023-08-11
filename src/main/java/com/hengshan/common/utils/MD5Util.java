package com.hengshan.common.utils;


import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public class MD5Util {

    /**
     * 对源数据生成MD5摘要
     *
     * @param source 源数据
     * @return MD5摘要
     */
    public static String md5Digest(String source) {
        return DigestUtils.md5DigestAsHex(source.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 对源数据加盐混淆后生成MD5摘要
     *
     * @param source 源数据
     * @param salt   盐值
     * @return MD5摘要
     */
    public static String md5Digest(String source, Integer salt) {
        //源数据混淆
        char[] ca = source.toCharArray();//字符数组
        for (int i = 0; i < ca.length; i++) {
            ca[i] = (char) (ca[i] + salt);
        }
        String target = new String(ca);
        String md5 = DigestUtils.md5DigestAsHex(target.getBytes(StandardCharsets.UTF_8));
        return md5;
    }

    public static void main(String[] args) throws Exception {
        String origin = "hello word";
        String encrypt = MD5Util.md5Digest(origin);
        String encrypt1 = MD5Util.md5Digest(origin,111);
        String encrypt2 = MD5Util.md5Digest(origin,222);
        System.out.println(encrypt);
        System.out.println(encrypt1);
        System.out.println(encrypt2);
    }
}
