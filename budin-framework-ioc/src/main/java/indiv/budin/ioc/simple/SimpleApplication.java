package indiv.budin.ioc.simple;

import indiv.budin.ioc.annotations.IocApplication;
import indiv.budin.ioc.applications.BudinIocApplication;

/**
 * @author
 * @date 2022/11/16 19 05
 * discription
 */
@IocApplication
public class SimpleApplication {
    public static void main(String[] args) {
        BudinIocApplication.run(SimpleApplication.class);
    }
}
