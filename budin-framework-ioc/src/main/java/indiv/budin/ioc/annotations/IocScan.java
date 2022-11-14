package indiv.budin.ioc.annotations;

import java.lang.annotation.*;

/**
 * 运行时可以读取
 * 修饰类
 * @Documented 表示是否能够包含在javadoc
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface IocScan {
    String value() default "";
}
