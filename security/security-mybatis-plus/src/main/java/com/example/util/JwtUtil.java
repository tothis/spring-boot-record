package com.example.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 李磊
 * @datetime 2020/6/23 11:18
 * @description
 */
public class JwtUtil {
    public static final String TOKEN_HEADER = "authorization";
    private static final String SECRET = "secret";
    private static final String ISS = "李磊";

    // 过期时间是3600秒 既1个小时
    private static final long EXPIRE_TIME = 3600L;

    // '记住我'过期时间为7天
    private static final long EXPIRE_REMEMBER = 604800L;

    // 创建token
    public static String createToken(Authentication authResult, boolean isRememberMe) {
        long expiration = isRememberMe ? EXPIRE_REMEMBER : EXPIRE_TIME;
        // token组成部分 1.头部(header) 2.载荷(payload) 3.签证(signature)

        // 指定签名时使用的算法 即header部分 jjwt已封装好
        SignatureAlgorithm algorithm = SignatureAlgorithm.HS512;

        String userName = authResult.getName();
        // 创建payload私有声明
        Map<String, Object> claims = new HashMap<>();
        claims.put("authResult", authResult.getAuthorities());

        // 为payload添加标准声明和私有声明
        String token = Jwts.builder()
                .setClaims(claims)
                // jwt唯一标识 可省略
                .setId(StringUtil.uuid())
                // 设置算法和秘钥
                .signWith(algorithm, SECRET)
                // jwt签发人
                .setIssuer(ISS)
                .setSubject(userName)
                .setIssuedAt(new Date()) // 签发时间
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000)) // 过期时间
                .compact();

        String uuid = StringUtil.uuid();
        RedisUtil.set(uuid, token, 30, TimeUnit.DAYS);
        return uuid;
    }

    // 解析token
    public static String parserToken(String token) {
        return body(token).getSubject();
    }

    // 是否已过期
    public static boolean isExpire(String token) {
        return body(token).getExpiration().before(new Date());
    }

    public static List<GrantedAuthority> authorities(String token) {
        return (List<GrantedAuthority>) body(token).get("authResult");
    }

    private static Claims body(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(RedisUtil.get(token))
                .getBody();
    }
}