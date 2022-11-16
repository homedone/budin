package indiv.budin.ioc.containers;

import indiv.budin.ioc.test.UserService;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface IocContainer {
    
    public boolean containsBean(String name);
    public Object getBean(String name);

    void iocScan(Class<?> Clazz);

    void scan(Set<Class<?>> packageClass);

    List<Class<?>> getClassesByInterface(Class<?> clazz);

    Map<String, Object> getBeanContainer();

    Set<Class<?>> getAllClasses();
}
