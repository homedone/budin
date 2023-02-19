package indiv.budin.rpc.irpc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface RpcAutowire {
    String serviceName() default "";
    String version() default "1.0.0";
    String node() default "1";
}
