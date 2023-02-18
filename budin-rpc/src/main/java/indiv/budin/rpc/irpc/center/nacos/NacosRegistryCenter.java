package indiv.budin.rpc.irpc.center.nacos;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import indiv.budin.rpc.irpc.center.base.RegistryCenter;
import indiv.budin.rpc.irpc.exception.NacosDiscoveryException;
import indiv.budin.rpc.irpc.exception.NacosRegistryException;

import java.net.InetSocketAddress;
import java.util.List;

public class NacosRegistryCenter implements RegistryCenter {
    private final static String DEFAULT_NACOS_CENTER_ADDRESS="127.0.0.1:9019";
    private static volatile NamingService namingService;

    public NacosRegistryCenter() {
        getNacosService(DEFAULT_NACOS_CENTER_ADDRESS);
    }

    public NacosRegistryCenter(String address) {
        getNacosService(address);
    }
    NamingService getNacosService(String address){
        if (namingService==null){
            synchronized (RegistryCenter.class){
                if (namingService==null){
                    try {
                        namingService = NamingFactory.createNamingService(address);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return namingService;
    }

    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        try {
            namingService.registerInstance(serviceName, inetSocketAddress.getHostName(), inetSocketAddress.getPort());
        } catch (NacosException e) {
            throw new NacosRegistryException(" <NACOS> server register fail");
        }
    }

    @Override
    public InetSocketAddress discovery(String serviceName) {
        try {
            List<Instance> instances = namingService.getAllInstances(serviceName);
            Instance instance = instances.get(0);
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        } catch (NacosException e) {
            throw new NacosDiscoveryException(" <NACOS> server discovery fail");
        }
    }

    @Override
    public void destroy(String serviceName) {

    }
}
