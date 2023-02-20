package indiv.budin.rpc.irpc.ioc;

import indiv.budin.ioc.annotations.IocController;
import indiv.budin.ioc.containers.AnnotationContainer;
import indiv.budin.rpc.irpc.annotation.RpcAutowire;
import indiv.budin.rpc.irpc.annotation.RpcService;
import indiv.budin.rpc.irpc.carrier.ServiceConfig;
import indiv.budin.rpc.irpc.center.base.ServiceCenter;
import indiv.budin.rpc.irpc.center.nacos.NacosServiceCenter;
import indiv.budin.rpc.irpc.common.utils.FactoryUtil;
import indiv.budin.rpc.irpc.commu.nio.Client;
import indiv.budin.rpc.irpc.commu.nio.netty.NettyClient;
import indiv.budin.rpc.irpc.proxy.ClientProxy;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class RpcProxyContainer {
    private final Map<String, Object> container;

    private final AnnotationContainer annotationContainer;

    private final Client client;


    public RpcProxyContainer() {
        this.container = new ConcurrentHashMap<>();
        annotationContainer = (AnnotationContainer) AnnotationContainer.getInstance();
        client = new NettyClient();

    }

    public void injectProxy() {
        Set<String> classesByAnnotation = annotationContainer.getKeyByAnnotation(IocController.class);
        for (String s : classesByAnnotation) {
            Object obj = annotationContainer.getBean(s);
            Class<?> clazz = obj.getClass();
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(RpcAutowire.class)) {
                    RpcAutowire rpcAutowire = field.getAnnotation(RpcAutowire.class);
                    ServiceConfig serviceConfig = new ServiceConfig();
                    serviceConfig.setServiceName(rpcAutowire.serviceName());
                    serviceConfig.setVersion(rpcAutowire.version());
                    serviceConfig.setNodeName(rpcAutowire.node());

                    ClientProxy clientProxy = new ClientProxy(client, serviceConfig);
                    Object proxyObject = clientProxy.getProxyInstance(field.getType());
                    field.setAccessible(true);
                    try {
                        field.set(obj, proxyObject);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }


    }

    public Object getBean(String s) {
        if (container.containsKey(s)) return container.get(s);
        return null;
    }
}
