package indiv.budin.ioc.annotations;

import java.lang.annotation.*;

/**
 *  运行时可以读取
 *  修饰方法,属性
 *  IocBean 作用于方法，产生一个单例
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface IocBean {

}
