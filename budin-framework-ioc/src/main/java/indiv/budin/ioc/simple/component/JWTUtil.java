package indiv.budin.ioc.simple.component;
import indiv.budin.ioc.annotations.IocComponent;
import indiv.budin.ioc.annotations.IocConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author
 * @date 2022/10/25 20 23
 * discription
 */
@IocComponent
@IocConfiguration(prefix = "config.jwt")
public class JWTUtil {
    private static Logger logger = LoggerFactory.getLogger(JWTUtil.class);
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
        JWTUtil.expireTime = expireTime * 1000L * 60;
    }

    public void setRestTime(long restTime) {
        JWTUtil.restTime = restTime * 1000L * 60;
    }


}
