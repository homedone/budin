package indiv.budin.usercenter.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import indiv.budin.common.exceptions.CommonException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author
 * @date 2022/10/25 20 23
 * discription
 */
@Component
@ConfigurationProperties(prefix = "config.jwt")
public class JWTUtil {
    //定义token返回头部
    public static String header;

    //token前缀
    public static String tokenPrefix;

    //签名密钥
    public static String secret;

    //有效期
    public static long expireTime;
    //达到更新条件的更新剩余时间
    public static long restTime;

    //存进客户端的token的key名
    public static final String USER_LOGIN_TOKEN = "USER_LOGIN_TOKEN";

    /**
     * 创建tocken
     *
     * @param subject
     * @return
     */

    public static String createToken(String subject) {
        Date expireDate = new Date(System.currentTimeMillis() + expireTime);
        return tokenPrefix + JWT.create()
                .withSubject(subject)
                .withExpiresAt(expireDate)
                .sign(Algorithm.HMAC512(secret));
    }

    /**
     * 验证tocken
     *
     * @param token
     * @return
     */
    public static String validateToken(String token) {
        try {
            return JWT.require(Algorithm.HMAC512(secret))
                    .build()
                    .verify(token.replace(tokenPrefix, ""))
                    .getSubject();
        } catch (TokenExpiredException e) {
            throw new CommonException("token已经过期");
        } catch (Exception e) {
            throw new CommonException("token验证失败");
        }
    }

    /**
     * 获取token的过期时间
     *
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token) {
        return JWT.require(Algorithm.HMAC512(secret))
                .build()
                .verify(token.replace(tokenPrefix, ""))
                .getExpiresAt();
    }

    public boolean isNearValidate(String token) {
        return (getExpirationDateFromToken(token).getTime() - System.currentTimeMillis()) < Math.min(restTime, (expireTime >> 2));
    }



    public void setHeader(String header) {
        JWTUtil.header = header;
    }


    public void setTokenPrefix(String tokenPrefix) {
        JWTUtil.tokenPrefix = tokenPrefix;
    }

    public void setSecret(String secret) {
        JWTUtil.secret = secret;
    }


    public void setExpireTime(long expireTime) {
        JWTUtil.expireTime = expireTime;
    }

    public void setRestTime(long restTime) {
        JWTUtil.restTime = restTime;
    }


}
