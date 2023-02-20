package indiv.budin.rpc.irpc.proxy;

import indiv.budin.aop.proxy.BaseProxy;
import indiv.budin.common.utils.UuidUtil;
import indiv.budin.rpc.irpc.carrier.RpcRequest;
import indiv.budin.rpc.irpc.carrier.ServiceConfig;
import indiv.budin.rpc.irpc.common.utils.FactoryUtil;
import indiv.budin.rpc.irpc.commu.nio.Client;
import indiv.budin.rpc.irpc.commu.nio.netty.NettyClient;
import indiv.budin.rpc.irpc.exception.ProxyException;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ClientProxy implements InvocationHandler, MethodInterceptor {
    Logger logger=LoggerFactory.getLogger(ClientProxy.class);
    private final Client client;
    private final ServiceConfig serviceConfig;

    public ClientProxy(Client client, ServiceConfig serviceConfig) {
        this.client = client;
        this.serviceConfig = serviceConfig;
    }

    public Object getProxyInstance(Class<?> clazz) {
        Class<?>[] interfaces = clazz.getInterfaces();
        if (interfaces.length > 0 || clazz.isInterface()) {
            return getProxyInstanceByJDK(clazz);
        }
        return getProxyInstanceByCglib(clazz);
    }

    Object getProxyInstanceByJDK(Class<?> clazz) {
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    Object getProxyInstanceByCglib(Class<?> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return invokes(method.getDeclaringClass().getName(),args,method);
    }

    public Object invokes(String interfaceName,Object[] args,Method method){
        logger.info(interfaceName+"  "+ method.getName());
        RpcRequest request=new RpcRequest();
        request.setInterfaceName(interfaceName);
        request.setMessageVersion(serviceConfig.getVersion());
        request.setMessageId(UuidUtil.makeUuid());
        request.setMethodName(method.getName());
        request.setParameters(args);
        request.setParamTypes(method.getParameterTypes());
        request.setNodeName(serviceConfig.getNodeName());
        request.setServiceName(serviceConfig.getServiceName());
        try{
            Object obj = client.sendObject(request);
            return obj;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("client proxy fail");
            throw new ProxyException();
        }
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return invokes(method.getDeclaringClass().getName(),objects,method);
    }
}
