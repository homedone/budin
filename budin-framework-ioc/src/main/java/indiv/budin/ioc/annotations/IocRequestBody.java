package indiv.budin.ioc.annotations;

import java.lang.annotation.*;

/**
 * 每个服务只允许一个带请求体的参数
 * 请求体参数名严格 对标 json参数名，递归调用注入属性
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Documented
public @interface IocRequestBody {
}
