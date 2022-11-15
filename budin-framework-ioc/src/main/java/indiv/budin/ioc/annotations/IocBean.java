package indiv.budin.ioc.annotations;

import java.lang.annotation.*;

/**
 *  运行时可以读取
 *  修饰方法,属性
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.METHOD})
public @interface IocBean {
    String name() default "";
}
