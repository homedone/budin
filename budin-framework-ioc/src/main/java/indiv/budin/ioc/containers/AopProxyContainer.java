package indiv.budin.ioc.containers;

import indiv.budin.aop.annotations.AopAspect;
import indiv.budin.aop.annotations.AopBackward;
import indiv.budin.aop.annotations.AopForward;
import indiv.budin.aop.annotations.AopPointCut;
import indiv.budin.aop.proxy.EnhanceProxy;
import indiv.budin.ioc.exceptions.NullArgException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AopProxyContainer implements ProxyContainer{

    Logger logger= LoggerFactory.getLogger(AopProxyContainer.class);
    private static volatile AopProxyContainer aopProxyContainer;
    private ConcurrentHashMap<String, Object> container;

    private ConcurrentHashMap<String, Method> targetMethod;
    private ConcurrentHashMap<String, List<Method>> pointMethod;
    private ConcurrentHashMap<String, String> point;
    private ConcurrentHashMap<String, Object> pointEnhance;
    private ConcurrentHashMap<String, Object> pointObject;

    private AnnotationContainer annotationContainer;

    private AopProxyContainer() {
        annotationContainer = (AnnotationContainer) AnnotationContainer.getInstance();
        container = new ConcurrentHashMap<>();
        point = new ConcurrentHashMap<>();
        targetMethod = new ConcurrentHashMap<>();
        pointMethod = new ConcurrentHashMap<>();
        pointEnhance = new ConcurrentHashMap<>();
        pointObject = new ConcurrentHashMap<>();
    }

    @Override
    public Object getBean(String s){
        if (container.containsKey(s)) return container.get(s);
        return null;
    }

    @Override
    public boolean containsBean(String name) {
        return container.containsKey(name);
    }

    @Override
    public void put(String name, Object bean) {
        container.put(name,bean);
    }
    public void injectProxy() {
        // 获取切面类集合
        Set<Class<?>> classesByAnnotation = annotationContainer.getClassesByAnnotation(AopAspect.class);

        // 找到切点
        for (Class<?> clazz : classesByAnnotation) {
            if (clazz.getAnnotation(AopAspect.class).value() == null) continue;
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(AopPointCut.class)) {
                    AopPointCut annotation = method.getAnnotation(AopPointCut.class);
                    if (annotation.name() == null || annotation.value() == null || targetMethod.containsKey(annotation.value())) {
                        throw new NullArgException("AopPointCut args can not be null or exist");
                    }
                    pointMethod.put(annotation.value(), new ArrayList<>());
                    point.put(annotation.name(),annotation.value());
                }
            }
            for (Method method : clazz.getDeclaredMethods()) {
                String s = null;
                if (method.isAnnotationPresent(AopForward.class)) {
                    s = method.getAnnotation(AopForward.class).execution();
                } else if (method.isAnnotationPresent(AopBackward.class)) {
                    s = method.getAnnotation(AopBackward.class).execution();
                }
                if (s != null) {
                    pointMethod.get(point.get(s)).add(method);
                    pointEnhance.put(point.get(s), annotationContainer.getBean(clazz.getName()));
                }
            }
        }
        // 找到切入点对应的方法
        for (Class<?> clazz : annotationContainer.getAllClasses()) {
            for (Method method : clazz.getDeclaredMethods()) {
                String methodName = clazz.getName() + "." + method.getName();
                if (pointMethod.containsKey(methodName)) {
                    targetMethod.put(methodName, method);
                    pointObject.put(methodName, annotationContainer.getBean(clazz.getName()));
                }
            }
        }
    }

    @Override
    public void doProxy(){
        injectProxy();
        // 生成代理对象
        for (String s: pointObject.keySet()) {
            Object obj=pointObject.get(s);
            if(annotationContainer.containsBean(obj.getClass().getName())){
                EnhanceProxy enhanceProxy = new EnhanceProxy(obj);
                Object proxyObject = enhanceProxy.getProxyInstance();
                container.put(obj.getClass().getName(),proxyObject);
            }
        }
    }

    Object notice(Object pointObj, Method pointMethod, Object[] args) {
        try {
            return pointMethod.invoke(pointObj, args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void noticeByAnnotation(String methodName, Class<? extends Annotation> annotation) {
        Object pointObj = pointEnhance.get(methodName);
        if (targetMethod.containsKey(methodName)) {
            for (Method mth : pointMethod.get(methodName)) {
                if (mth.isAnnotationPresent(annotation)) {
                    notice(pointObj, mth, new Object[]{});
                }
            }
        }
    }

    public static AopProxyContainer getInstance() {
        if (aopProxyContainer == null) {
            synchronized (AopProxyContainer.class) {
                if (aopProxyContainer == null) {
                    aopProxyContainer = new AopProxyContainer();

                }
            }
        }
        return aopProxyContainer;
    }

}
