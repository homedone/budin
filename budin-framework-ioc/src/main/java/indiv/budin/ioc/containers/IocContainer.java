package indiv.budin.ioc.containers;

import java.lang.annotation.Annotation;
import java.util.Set;

public interface IocContainer {
    public Object getBean(String name);

    public Set<Class<?>> getClassesByAnnotation(Class<? extends Annotation> annotation);
}
