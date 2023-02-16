package indiv.budin.ioc.test;


import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxy implements MethodInterceptor {
    Object object;

    public CglibProxy(Object object) {
        this.object = object;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("cglib make");
        Object invoke = method.invoke(object, objects);
        return invoke;
    }
    public static CglibProxy create(Object object){
        return new CglibProxy(object);
    }
    public Object getProxyInstance(Class<?> clazz){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }
    public Object getProxyInstance(){
        return getProxyInstance(object.getClass());
    }

    public static void main(String[] args) {
        User user=new User();
        User proxyInstance = (User) CglibProxy.create(user).getProxyInstance();
        String pay = proxyInstance.pay(16);
        System.out.println(pay);
    }
}
