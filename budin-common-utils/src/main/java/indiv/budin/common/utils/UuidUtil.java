package indiv.budin.common.utils;

import java.util.UUID;

/**
 * @author
 * @date 2022/10/12 17 15
 * discription
 */
public class UuidUtil {
    public static String makeUuid(){
        return UUID.randomUUID().toString().replace("-", "");
    }
}
