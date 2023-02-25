package indiv.budin.ioc.containers;

import indiv.budin.ioc.annotations.IocAutowired;
import indiv.budin.ioc.annotations.IocConfiguration;
import indiv.budin.ioc.constants.ExceptionMessage;
import indiv.budin.ioc.exceptions.NoBeanException;
import indiv.budin.ioc.exceptions.NotFindClassException;
import indiv.budin.ioc.utils.StringUtil;
import indiv.budin.ioc.utils.YamlUtil;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author
 * @date 2022/11/16 20 26
 * discription
 */
public class ProxyDependencyInjector implements DependencyInjector {
    private ProxyContainer budinProxyContainer;

    private IocContainer iocContainer;
    private Map<String, Object> yamlMap;

    public ProxyDependencyInjector() {
        iocContainer=AnnotationContainer.getInstance();
        budinProxyContainer = AopProxyContainer.getInstance();
    }

    public static ProxyDependencyInjector creator() {
        return new ProxyDependencyInjector();
    }

    public ProxyDependencyInjector scan(Set<Class<?>> packageClass) {
        iocContainer.scan(packageClass);
        return this;
    }

    public DependencyInjector config(Map config) {
        yamlMap = config;
        return this;
    }

    @Override
    public ProxyDependencyInjector inject() {
        budinProxyContainer.doProxy();
        for (Class<?> clazz : iocContainer.getAllClasses()) {
            setAttributionByConfiguration(clazz);
            setAttributionByAutowired(clazz);
        }
        return this;
    }

    public IocContainer getIocContainer() {
        return iocContainer;
    }

    Object getBean(String s){
        Object obj = budinProxyContainer.getBean(s);
        if (obj==null){
            obj=iocContainer.getBean(s);
        }
        return obj;
    }

    /**
     * 通过yaml配置注入
     *
     * @param clazz
     */
    public void setAttributionByConfiguration(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(IocConfiguration.class)) {
            return;
        }
        if (!iocContainer.containsBean((clazz.getName()))) {
            throw new NoBeanException(ExceptionMessage.NO_BEAN_EXCEPTION);
        }
        Object clazzObj = this.getBean(clazz.getName());
        IocConfiguration annotation = clazz.getAnnotation(IocConfiguration.class);
        String prefix = annotation.prefix();
        Map objectMap = YamlUtil.getObjectMapByPrefix(yamlMap, prefix);
        try {
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                String fieldName = field.getName();
                if (!objectMap.containsKey(fieldName)) continue;
                Object fieldObj = objectMap.get(fieldName);
//                Object obj = ClassUtil.stringToTarget(String.valueOf(fieldObj), field.getType());
                field.set(clazzObj, fieldObj);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过类型注入，如果失败则通过注解中的名称注入
     * 先扫描属性注解，再遍历方法，如果在属性注解注入的时候已经注入，则方法的注解不注入
     *
     * @param clazz
     */

    public void setAttributionByAutowired(Class<?> clazz) {
        if (!iocContainer.containsBean((clazz.getName()))) {
            throw new NoBeanException(ExceptionMessage.NO_BEAN_EXCEPTION);
        }
        Object clazzObj = this.getBean(clazz.getName());
        for (Field field : clazz.getDeclaredFields()) {
            setAttributionByFieldAutowired(field, clazzObj);
        }
        for (Method method : clazz.getDeclaredMethods()) {
            //如果是set方法，则注入
            setAttributionBySetMethodAutowired(clazz, method, clazzObj);
        }

    }

    /**
     * 直接通过field注入，基本逻辑，如果是接口，找实现类，无实现类则抛出异常，有一个实现类直接注入
     * 多个实现类通过注解名称注入，如果不是接口，直接根据类型注入
     *
     * @param
     * @param
     */
    public void setAttributionByFieldAutowired(Field field, Object clazzObj) {
        field.setAccessible(true);
        if (!field.isAnnotationPresent(IocAutowired.class)) return;
        Class<?> type = field.getType();
        //如果不是一个接口，直接注入
        IocAutowired iocAutowired = field.getAnnotation(IocAutowired.class);
        String iocAutowiredName = iocAutowired.name();
        Object obj = getObjectByType(type);
        if (obj == null) obj = getByIocAutowiredName(iocAutowiredName);
        try {
            //只有属性为空时才注入
            if (field.get(clazzObj) == null) field.set(clazzObj, obj);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通过set方法注入，基本逻辑，如果是接口，找实现类，无实现类则抛出异常，有一个实现类直接注入
     * 多个实现类通过注解名称注入，如果不是接口，直接根据类型注入，多了一步根据get方法判断属性是否已经注入
     */
    public void setAttributionBySetMethodAutowired(Class<?> clazz, Method method, Object clazzObj) {
        method.setAccessible(true);
        //只能作用在set方法上
        if (!method.isAnnotationPresent(IocAutowired.class) ||
                !StringUtil.isPrefixMethod(method.getName(), "set")) return;
        Class<?> type = method.getParameterTypes()[0];
        IocAutowired iocAutowired = method.getAnnotation(IocAutowired.class);
        String iocAutowiredName = iocAutowired.name();
        //根据类型找bean
        Object obj = getObjectByType(type);
        //如果没找到,根据名称找
        if (obj == null) obj = getByIocAutowiredName(iocAutowiredName);
        try {
            String getMethodName = StringUtil.getPrefixMethod(type.getName(), "get");
            Method getMethod = clazz.getMethod(getMethodName);
            Object invoke = getMethod.invoke(clazzObj);
            if (invoke == null) method.invoke(clazzObj, obj);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Object getObjectByType(Class<?> type) {
        String objKey;
        if (!type.isInterface()) {
            objKey = type.getName();
            if (!iocContainer.containsBean(objKey)) {
                //如果类型找不到bean，则直接返回
                return null;
            }
        }
        //否则获取接口的具有实例的所有实现类
        List<Class<?>> classesByInterface = iocContainer.getClassesByInterface(type);
        //如果没有实现类,抛出找不到实现类的异常,如果只有一个实现类,按这个实现类注入,如果不止一个实现类,则改为名称注入
        if (classesByInterface == null || classesByInterface.size() == 0) {
            throw new NotFindClassException(ExceptionMessage.NOT_FIND_CLASS_EXCEPTION);
        } else if (classesByInterface.size() == 1) {
            Class<?> implement = classesByInterface.get(0);
            objKey = implement.getName();
        } else {
            return null;
        }
        return this.getBean(objKey);
    }

    public Object getByIocAutowiredName(String iocAutowiredName) {
        if (!iocContainer.containsBean(iocAutowiredName)) {
            throw new NoBeanException(ExceptionMessage.NO_BEAN_EXCEPTION);
        }
        return this.getBean(iocAutowiredName);
    }
}
