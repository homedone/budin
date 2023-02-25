package indiv.budin.aop.annotations;

import java.lang.annotation.*;

/**
 * 暂时不能增强到接口的实现类，除非删除jdk动态代理，全面采用cglib动态代理
 * 否则无法和rpc框架对接
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AopAspect {
    String value() default "";
}
