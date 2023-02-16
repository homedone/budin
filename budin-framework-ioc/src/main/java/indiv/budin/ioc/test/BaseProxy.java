package indiv.budin.ioc.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class BaseProxy implements InvocationHandler {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    Object obj;

    public BaseProxy(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("message");
        Object invoke = method.invoke(obj, args);
        return invoke;
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        People people =(People) Proxy.newProxyInstance(BaseProxy.class.getClassLoader(),User.class.getInterfaces(),new BaseProxy(new User()));
        String pay = people.pay(15);
        System.out.println(pay);

    }
}
