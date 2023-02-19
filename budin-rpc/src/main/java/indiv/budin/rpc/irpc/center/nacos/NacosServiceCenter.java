package indiv.budin.rpc.irpc.center.nacos;

import indiv.budin.rpc.irpc.carrier.ServiceConfig;
import indiv.budin.rpc.irpc.center.base.RegistryCenter;
import indiv.budin.rpc.irpc.center.base.ServiceCenter;
import indiv.budin.rpc.irpc.exception.RpcServiceException;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class NacosServiceCenter implements ServiceCenter {
    private final RegistryCenter registryCenter=NacosRegistryCenter.getInstance();

    private final Map<String,Object> serviceContainer= new ConcurrentHashMap<>();
    @Override
    public Object getService(String serviceName) {
        if (!serviceContainer.containsKey(serviceName)){
            String message=serviceName+" no in service center";
            throw new RpcServiceException(message);
        }
        Object obj = serviceContainer.get(serviceName);
        return obj;
    }

    @Override
    public void addService(ServiceConfig serviceConfig, Object service) {
        String serviceName = serviceConfig.getServiceNameWithNode();
        if (serviceContainer.containsKey(serviceName)) return;
        serviceContainer.put(serviceName,service);
        registryCenter.register(serviceName,new InetSocketAddress(serviceConfig.getHost(),serviceConfig.getPort()));
    }
}
