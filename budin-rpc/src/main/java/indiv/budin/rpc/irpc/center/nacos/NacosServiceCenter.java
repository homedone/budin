package indiv.budin.rpc.irpc.center.nacos;

import indiv.budin.rpc.irpc.balance.ConsistentHashLoadBalancer;
import indiv.budin.rpc.irpc.balance.LoadBalancer;
import indiv.budin.rpc.irpc.carrier.ServiceConfig;
import indiv.budin.rpc.irpc.center.base.RegistryCenter;
import indiv.budin.rpc.irpc.center.base.ServiceCenter;
import indiv.budin.rpc.irpc.exception.RpcServiceException;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NacosServiceCenter implements ServiceCenter {
    private final RegistryCenter registryCenter=NacosRegistryCenter.getInstance();


    public NacosServiceCenter() {

    }

    private final Map<String,Object> serviceContainer= new ConcurrentHashMap<>();
    @Override
    public Object getService(String serviceNameWithNode) {
        if (!serviceContainer.containsKey(serviceNameWithNode)){
            String message=serviceNameWithNode+" no in service center";
            throw new RpcServiceException(message);
        }
        Object obj = serviceContainer.get(serviceNameWithNode);
        return obj;
    }

    @Override
    public void addService(ServiceConfig serviceConfig, Object service) {
        String serviceNameWithNode = serviceConfig.getServiceNameWithNode();
        String serviceName = serviceConfig.getServiceName();
        if (serviceContainer.containsKey(serviceNameWithNode)) return;
        serviceContainer.put(serviceNameWithNode,service);
        registryCenter.register(serviceName,new InetSocketAddress(serviceConfig.getHost(),serviceConfig.getPort()));
    }
}
