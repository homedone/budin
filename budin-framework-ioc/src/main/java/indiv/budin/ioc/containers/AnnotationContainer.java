package indiv.budin.ioc.containers;

import indiv.budin.ioc.annotations.IocBean;
import indiv.budin.ioc.annotations.IocComponent;
import indiv.budin.ioc.annotations.IocScan;
import indiv.budin.ioc.annotations.IocService;
import indiv.budin.ioc.constants.ExceptionMessage;
import indiv.budin.ioc.exceptions.NoScanerException;
import indiv.budin.ioc.utils.PackageUtil;
import indiv.budin.ioc.utils.StringUtil;

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
        this.addToContainer(packageClass, IocComponent.class);
        this.addToContainer(packageClass, IocService.class);
    }

    public void setAttributionByFieldAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(annotation)) {
                String name= field.getType().getName();
                System.out.println("name: "+name);
                Method method;
                if (!beanContainer.containsKey(name)) {
                    IocBean iocBean=field.getAnnotation(IocBean.class);
                    if (iocBean!=null && beanContainer.containsKey(iocBean.name())) {
                        name = iocBean.name();
                    } else continue;
                }
                Object obj=beanContainer.get(name);
//                System.out.println(name);
                String[] nameSplit = name.split("\\.");
                try {
                    String paramClassName = nameSplit[nameSplit.length-1];
                    System.out.println(paramClassName);
                    method = clazz.getDeclaredMethod("set" + paramClassName);
//                    method.invoke();
                } catch (NoSuchMethodException e) {
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
        try {
            return beanContainer.get(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
