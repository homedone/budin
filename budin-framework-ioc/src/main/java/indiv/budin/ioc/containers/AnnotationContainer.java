package indiv.budin.ioc.containers;

import indiv.budin.ioc.annotations.IocComponent;
import indiv.budin.ioc.annotations.IocScan;
import indiv.budin.ioc.constants.ExceptionMessage;
import indiv.budin.ioc.exceptions.NoScanerException;
import indiv.budin.ioc.utils.PackageUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author
 * @date 2022/11/14 19 26
 * discription
 */
public class AnnotationContainer implements IocContainer {
    private final ConcurrentHashMap<String, Object> beanContainer = new ConcurrentHashMap<>();


    public AnnotationContainer() {

    }

    public void iocScan(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(IocScan.class)) {
            throw new NoScanerException(ExceptionMessage.NO_SCAN_EXCEPTION);
        }
        IocScan iocScan = clazz.getAnnotation(IocScan.class);
        String scanPath = iocScan.value();
        Set<Class<?>> packageClass = PackageUtil.getPackageClass(scanPath);
        this.addToContainer(packageClass,IocComponent.class);
    }

    public void setAttributionByFieldAnnotation(Class<?> clazz,Class<? extends Annotation> annotation)  {
        Object obj = beanContainer.get(clazz.getName());
        for (Field field : clazz.getFields()) {
            field.setAccessible(true);
            if(field.isAnnotationPresent(annotation)){
                String name = field.getName();
                String type = field.getType().getName();
                Method method;
                try {
                    if (!beanContainer.contains(type)){
                        if(beanContainer.contains(name)){
                            method=clazz.getDeclaredMethod("a");
                        }
                    }
                }catch (NoSuchMethodException e){
                    e.printStackTrace();
                }
            }
        }

    }

    public void addToContainer(Set<Class<?>> packageClass, Class<? extends Annotation> annotation) {
        try {
            for (Class<?> packageClazz : packageClass) {
                if (packageClazz.isAnnotationPresent(annotation)) {
                    beanContainer.put(packageClazz.getName(), packageClazz.newInstance());
                }
            }
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getBean(String name) {
        return beanContainer.get(name);
    }

    @Override
    public Set<Class<?>> getClassesByAnnotation(Class<? extends Annotation> annotation) {
        Set<Class<?>> annotationSet = new HashSet<>();
        Set<String> keySet = beanContainer.keySet();
        for (String key : keySet) {
            Class<?> clazz = beanContainer.get(key).getClass();
            if (clazz.isAnnotationPresent(annotation)) {
                annotationSet.add(clazz);
            }
        }
        return annotationSet;
    }
}
