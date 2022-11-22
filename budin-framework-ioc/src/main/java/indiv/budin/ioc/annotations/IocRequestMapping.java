package indiv.budin.ioc.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface IocRequestMapping {
    String Url() default "";

    String method() default "get";
}
