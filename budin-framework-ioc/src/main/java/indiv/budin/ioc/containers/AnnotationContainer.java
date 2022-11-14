package indiv.budin.ioc.containers;

import indiv.budin.ioc.annotations.IocComponent;
import indiv.budin.ioc.annotations.IocScan;
import indiv.budin.ioc.constants.ExceptionMessage;
import indiv.budin.ioc.exceptions.NoScanerException;
import indiv.budin.ioc.utils.PackageUtil;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author
 * @date 2022/11/14 19 26
 * discription
 */
public class AnnotationContainer implements IocContainer {
    private ConcurrentHashMap<String, Object> beanContainer = new ConcurrentHashMap<>();


    public AnnotationContainer() {

    }

    public void iocScan(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(IocScan.class)) {
            throw new NoScanerException(ExceptionMessage.NO_SCAN_EXCEPTION);
        }
        IocScan iocScan = clazz.getAnnotation(IocScan.class);
        String scanPath = iocScan.value();
        Set<Class<?>> packageClass = PackageUtil.getPackageClass(scanPath);
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
        return null;
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
