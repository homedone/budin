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
        if (interfaces.length == 0) {
            return getProxyInstanceByCglib(clazz);
        }
        return getProxyInstanceByJDK(clazz);
    }

    Object getProxyInstanceByJDK(Class<?> clazz) {
        return Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class<?>[]{clazz}, this);
    }

    Object getProxyInstanceByCglib(Class<?> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return invoke(proxy.getClass().getName(),method,args);
    }

    public Object invoke(String interfaceName,Object[] args,Method method){
        RpcRequest request=new RpcRequest();
        request.setInterfaceName(interfaceName);
        request.setMessageVersion(serviceConfig.getVersion());
        request.setMessageId(UuidUtil.makeUuid());
        request.setMethodName(method.getName());
        request.setParameters(method.getParameters());
        request.setParamTypes(method.getParameterTypes());
        request.setNodeName(serviceConfig.getNodeName());
        request.setServiceName(serviceConfig.getServiceName());
        try{
            Object obj = client.sendObject(request);
            return obj;
        }catch (Exception e){
            logger.error("client proxy fail");
            throw new ProxyException();
        }
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return invoke(o.getClass().getName(),method,objects);
    }
}
