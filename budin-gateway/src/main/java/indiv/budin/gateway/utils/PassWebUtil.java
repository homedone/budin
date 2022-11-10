package indiv.budin.gateway.utils;

import java.util.HashSet;
import java.util.Set;

/**
 * @author
 * @date 2022/11/6 13 40
 * discription
 */
public class PassWebUtil {
    public static final Set<String> pass = new HashSet<>();

    static {
        pass.add("/center/user/login");
        pass.add("/center/user/register");
        pass.add("/center/test");
        pass.add("/center/logout");
        pass.add("/center/user/send/code");
    }

    public static boolean isPass(String url) {
        return pass.contains(url);
    }
}
