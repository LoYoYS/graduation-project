package com.yuan.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;


public class JwtUtil {
    //密匙
    private static final String SECRET = "yuan";
    //过期时间一个小时
    private static final long EXPIRATION = 3600*1000;

    /*
    * 获取token
    * */
    public static String getToken(String userName){
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create()
                // 添加头部
                .withHeader(map)
                // 放入用户的用户名
                .withAudience(userName)
                // 可以将基本信息放到claims中
                .withClaim("account", userName)
                //.withClaim("password", password)
                //超时设置,设置过期的日期
               .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION))
                // 签发时间
                .withIssuedAt(new Date())
                // SECRET加密
                .sign(Algorithm.HMAC256(SECRET));
        return token;
    }

    /**
     * 获取用户名
     */
    public static String getUserName(String token) {
        try {
            String userName = JWT.decode(token).getAudience().get(0);
            return userName;
        }catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 校验token
     * @param token
     * @return
     * */
    public static boolean verifyToken(String token) {
        try {
            Algorithm algorithm  = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            verifier.verify(token);
            return true;
        }catch (JWTVerificationException e) {
            return false;
        }
    }

}
