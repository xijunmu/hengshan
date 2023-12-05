package com.hengshan.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class JWTUtil {

    public static final Long JWT_TTL = 60 * 60 * 1000L;// 60 * 60 *1000 有效期为一个小时

    public static final String JWT_KEY = "hengshan";  //设置秘钥

    /**
     * 生成加密后的秘钥 secretKey
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(JWT_KEY);
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * 生成jwt
     *
     * @param uuid 唯一id
     * @param subject   token中要存放的数据（可以是JSON数据）
     * @param ttlMillis 过期时间
     */
    public static String createJWT(String uuid, String subject, Long ttlMillis) {
        if (uuid == null) {
            uuid = UUID.randomUUID().toString().replaceAll("-", "");
        }
        if (ttlMillis == null) {
            ttlMillis = JWT_TTL;
        }
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        Date expDate = new Date(nowMillis + ttlMillis);
        JwtBuilder builder = Jwts.builder()
                .setId(uuid)           // 唯一ID
                .setSubject(subject)   // 主题
                .setIssuer("mxj")      // 签发者
                .setIssuedAt(now)      // 签发时间
                .setExpiration(expDate)// 过期时间
                .signWith(signatureAlgorithm, secretKey); //使用HS256对称加密算法签名, 第二个参数为秘钥
        return builder.compact();
    }

    /**
     * 解析jwt
     *
     * @param jwt
     */
    public static Claims parseJWT(String jwt) {
        SecretKey secretKey = generalKey();
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwt).getBody();
    }

    public static void main(String[] args) throws Exception {
        String jwt = createJWT(null,"1234",null);
        System.out.println(jwt);
        Claims claims = parseJWT(jwt);
        System.out.println(claims.getSubject());
    }
}
