package indiv.budin.rpc.irpc.center.nacos;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import indiv.budin.rpc.irpc.center.base.RegistryCenter;
import indiv.budin.rpc.irpc.exception.RpcDiscoveryException;
import indiv.budin.rpc.irpc.exception.RpcRegistryException;

import java.net.InetSocketAddress;
import java.util.List;

public class NacosRegistryCenter implements RegistryCenter {
    private final static String DEFAULT_NACOS_CENTER_ADDRESS="127.0.0.1:9119";
    private NamingService namingService;
    private static volatile NacosRegistryCenter nacosRegistryCenter;

    private NacosRegistryCenter() {

    }

    public static NacosRegistryCenter getInstance(){
        return getInstance(DEFAULT_NACOS_CENTER_ADDRESS);
    }
    public static NacosRegistryCenter getInstance(String address){
        if (nacosRegistryCenter==null){
            synchronized (RegistryCenter.class){
                if (nacosRegistryCenter==null){
                    try {
                        nacosRegistryCenter = new NacosRegistryCenter();
                        nacosRegistryCenter.namingService= NamingFactory.createNamingService(address);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return nacosRegistryCenter;
    }

    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {
        try {
            namingService.registerInstance(serviceName, inetSocketAddress.getHostName(), inetSocketAddress.getPort());
        } catch (NacosException e) {
            throw new RpcRegistryException(" <NACOS> server register fail");
        }
    }

    @Override
    public InetSocketAddress discovery(String serviceName) {
        try {
            List<Instance> instances = namingService.getAllInstances(serviceName);
            Instance instance = instances.get(0);
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        } catch (NacosException e) {
            throw new RpcDiscoveryException(" <NACOS> server discovery fail");
        }
    }

    @Override
    public void destroy(String serviceName) {

    }
}
