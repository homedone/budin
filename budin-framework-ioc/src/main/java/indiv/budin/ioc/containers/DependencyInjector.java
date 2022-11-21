package indiv.budin.ioc.containers;

import java.util.Set;

/**
 * @author
 * @date 2022/11/16 20 40
 * discription
 */
public interface DependencyInjector {
    /**
     * 实现依赖注入
     */
    public DependencyInjector inject();

    DependencyInjector scan(Set<Class<?>> packageClass);

    public IocContainer getIocContainer();
}
