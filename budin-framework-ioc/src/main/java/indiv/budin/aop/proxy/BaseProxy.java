package indiv.budin.aop.proxy;

import com.beust.ah.A;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BaseProxy implements InvocationHandler, MethodInterceptor {
    Object obj;

    public BaseProxy(Object obj) {
        this.obj = obj;
    }

    public static BaseProxy create(Object obj) {
        return new BaseProxy(obj);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object invoke = method.invoke(obj, args);
        return invoke;
    }

    public Object getProxyInstance() {
        Class<?>[] interfaces = obj.getClass().getInterfaces();
        if (interfaces.length == 0) {
            return getProxyInstanceByCglib();
        }
        return getProxyInstanceByJDK();
    }

    Object getProxyInstanceByJDK() {
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), obj.getClass().getInterfaces(), this);
    }

    Object getProxyInstanceByCglib() {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(obj.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Object invoke = method.invoke(obj, objects);
        return invoke;
    }

    public static void main(String[] args) {
        Animal animal = new Dog();
        System.out.println(animal.introduce("dog"));
        Animal dog = (Animal) BaseProxy.create(animal).getProxyInstance();
        Cat cat = (Cat) BaseProxy.create(new Cat()).getProxyInstance();
        System.out.println(dog.introduce(" dog"));
        System.out.println(cat.introduce(" cat"));
    }
}

class Dog implements Animal {

    @Override
    public String introduce(String s) {
        return this.getClass().getName() + s;
    }
}

class Cat {
    public String introduce(String s) {
        return this.getClass().getName() + s;
    }
}

interface Animal {
    String introduce(String s);
}
