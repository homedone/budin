package indiv.budin.aop.proxy;

import indiv.budin.aop.annotations.AopBackward;
import indiv.budin.aop.annotations.AopForward;
import indiv.budin.ioc.containers.ProxyContainer;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class EnhanceProxy extends BaseProxy {

    private ProxyContainer proxyContainer;
    public EnhanceProxy(Object obj) {
        super(obj);
        proxyContainer=ProxyContainer.getInstance();
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = obj.getClass().getName() + "." + method.getName();
        proxyContainer.noticeByAnnotation(methodName, AopForward.class);
        Object invoke = super.invoke(proxy, method, args);
        proxyContainer.noticeByAnnotation(methodName, AopBackward.class);
        return invoke;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        String methodName = obj.getClass().getName() + "." + method.getName();
        proxyContainer.noticeByAnnotation(methodName, AopForward.class);
        Object intercept = super.intercept(o, method, objects, methodProxy);
        proxyContainer.noticeByAnnotation(methodName, AopBackward.class);
        return intercept;
    }

    public static void main(String[] args) {
        Animalt animal = new Dogd();
        EnhanceProxy enhanceProxy = new EnhanceProxy(animal);
        Animalt dog = (Animalt) enhanceProxy.getProxyInstance();
        Catt cat = (Catt) new EnhanceProxy(new Catt()).getProxyInstance();
        System.out.println(dog.introduce(" dog"));
        System.out.println(cat.introduce(" cat"));
    }
}

class Dogd implements Animalt {

    @Override
    public String introduce(String s) {
        return this.getClass().getName() + s;
    }
}

class Catt {
    public String introduce(String s) {
        return this.getClass().getName() + s;
    }

    public String cao(){
        return null;
    }
}

interface Animalt {
    String introduce(String s);
}