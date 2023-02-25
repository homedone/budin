package indiv.budin.rpc.irpc.annotation;

import java.lang.annotation.*;

/**
 * only be netty client
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface RpcClientApplication {
}
