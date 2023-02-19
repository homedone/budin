package indiv.budin.ioc.containers;

import indiv.budin.ioc.annotations.*;
import indiv.budin.ioc.constants.ExceptionMessage;
import indiv.budin.ioc.exceptions.NoBeanException;
import indiv.budin.ioc.exceptions.NoScanerException;
import indiv.budin.ioc.utils.PackageUtil;
import indiv.budin.ioc.utils.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author
 * @date 2022/11/14 19 26
 * discription
 */
public class AnnotationContainer implements IocContainer {
    private final ConcurrentHashMap<String, Object> beanContainer = new ConcurrentHashMap<>();

    private static volatile IocContainer iocContainer;


    private AnnotationContainer() {

    }

    @Override
    public void scan(Set<Class<?>> packageClass) {
        this.addToContainer(packageClass, IocService.class);
        this.addToContainer(packageClass, IocComponent.class);
        this.addToContainer(packageClass, IocController.class);
    }

    @Override
    public void iocScan(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(IocScan.class)) {
            throw new NoScanerException(ExceptionMessage.NO_SCAN_EXCEPTION);
        }
        IocScan iocScan = clazz.getAnnotation(IocScan.class);
        String scanPath = iocScan.value();
        Set<Class<?>> packageClass = PackageUtil.getPackageClass(scanPath);
        scan(packageClass);
    }

    public Map<String, Object> getBeanContainer() {
        return beanContainer;
    }

    public static IocContainer getInstance() {
        if (iocContainer == null) {
            synchronized (AnnotationContainer.class) {
                if (iocContainer == null) {
                    iocContainer = new AnnotationContainer();
                }
            }
        }
        return iocContainer;
    }

    @Override
    public List<Class<?>> getClassesByInterface(Class<?> clazz) {
        List<Class<?>> interfaceClassSet = null;
        Set<String> keySet = beanContainer.keySet();
        if (clazz.isInterface()) {
            if (keySet.size()==0) return null;
            interfaceClassSet = new ArrayList<>();
            for (String key : keySet) {
                Class<?> keyClass = beanContainer.get(key).getClass();
                if (clazz.isAssignableFrom(keyClass)) {
                    interfaceClassSet.add(keyClass);
                }
            }
        }
        return interfaceClassSet;
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
    public boolean containsBean(String name) {
        return beanContainer.containsKey(name);
    }

    @Override
    public Object getBean(String name) {
        try {
            return beanContainer.get(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Set<Class<?>> getAllClasses() {
        Set<Class<?>> allClassSet;
        Set<String> keySet = beanContainer.keySet();
        if (keySet.size() == 0) return null;
        allClassSet = new HashSet<>();
        for (String key : keySet) {
            Class<?> clazz = beanContainer.get(key).getClass();
            allClassSet.add(clazz);
        }
        return allClassSet;
    }

    public Set<Class<?>> getClassesByAnnotation(Class<? extends Annotation> annotation) {
        Set<Class<?>> annotationSet;
        Set<String> keySet = beanContainer.keySet();
        if (keySet.size() == 0) return null;
        annotationSet=new HashSet<>();
        for (String key : keySet) {
            Class<?> clazz = beanContainer.get(key).getClass();
            if (clazz.isAnnotationPresent(annotation)) {
                annotationSet.add(clazz);
            }
        }
        return annotationSet;
    }
    public Set<String> getKeyByAnnotation(Class<? extends Annotation> annotation) {
        Set<String> annotationSet;
        Set<String> keySet = beanContainer.keySet();
        if (keySet.size() == 0) return null;
        annotationSet=new HashSet<>();
        for (String key : keySet) {
            Class<?> clazz = beanContainer.get(key).getClass();
            if (clazz.isAnnotationPresent(annotation)) {
                annotationSet.add(key);
            }
        }
        return annotationSet;
    }
}
