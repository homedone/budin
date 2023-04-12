package indiv.budin.rpc.irpc.center.nacos;

import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import indiv.budin.rpc.irpc.balance.ConsistentHashLoadBalancer;
import indiv.budin.rpc.irpc.balance.LoadBalancer;
import indiv.budin.rpc.irpc.center.base.RegistryCenter;
import indiv.budin.rpc.irpc.exception.RpcDiscoveryException;
import indiv.budin.rpc.irpc.exception.RpcRegistryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class NacosRegistryCenter implements RegistryCenter {

    Logger logger = LoggerFactory.getLogger(NacosRegistryCenter.class);
    private final static String DEFAULT_NACOS_CENTER_ADDRESS = "127.0.0.1:8848";
    private NamingService namingService;
    private static volatile NacosRegistryCenter nacosRegistryCenter;

    private final LoadBalancer<String> loadBalancer;

    private NacosRegistryCenter() {
        this.loadBalancer = new ConsistentHashLoadBalancer();
    }

    public NacosRegistryCenter(LoadBalancer<String> loadBalancer) {
        this.loadBalancer = loadBalancer;
    }

    public static NacosRegistryCenter getInstance() {
        return getInstance(DEFAULT_NACOS_CENTER_ADDRESS);
    }

    public static NacosRegistryCenter getInstance(String address) {
        if (nacosRegistryCenter == null) {
            synchronized (NacosRegistryCenter.class) {
                if (nacosRegistryCenter == null) {
                    try {
                        nacosRegistryCenter = new NacosRegistryCenter();
                        nacosRegistryCenter.namingService = NamingFactory.createNamingService(address);
                    } catch (Exception e) {
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
            logger.info("serviceName: " + serviceName + " ip: " + inetSocketAddress.getHostName() + "::" + inetSocketAddress.getPort());
            namingService.registerInstance(serviceName, inetSocketAddress.getHostName(), inetSocketAddress.getPort());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RpcRegistryException(" <NACOS> server register fail");
        }
    }

    /**
     * clustered
     *
     * @param serviceName
     * @param requestKey
     * @return
     */
    //此处留待问题，负载中心和注册中心中的服务不同步，导致不可用，不一致性问题，
    // 服务死掉后 nacos触发健康事件，经历unHealthy状态，一段时间后才deregester
    // 需要保持哈希环与注册中心发现的服务一致。
    // 简单的解法是，可以遍历哈希环真实节点，与Healthy节点对比，多余的给remove掉
    // 这里预计需要优化，结合nacos做调整
    @Override
    public InetSocketAddress discovery(String serviceName, String requestKey) {
        try {
            logger.info(serviceName);
            List<Instance> instances = namingService.getAllInstances(serviceName);
            logger.info("Now service node size is " + instances.size());
            List<String> nodes = new ArrayList<>();
            for (Instance instance : instances) {
                if (instance.isEnabled() && instance.isHealthy()) {
                    String nd = instance.getIp() + "@" + instance.getPort();
                    nodes.add(nd);
                }else {
                    logger.error("------ service error");
                }
            }
            if (!loadBalancer.contains(serviceName)) {
                loadBalancer.putInto(nodes, serviceName);
            } else {
                loadBalancer.consistent(nodes, serviceName);
            }
            String node = loadBalancer.select(serviceName, requestKey);
            String[] strings = node.split("@");
            String ip = strings[0];
            int port = Integer.parseInt(strings[1]);
            return new InetSocketAddress(ip, port);
        } catch (Exception e) {
            throw new RpcDiscoveryException(" <NACOS> server discovery fail");
        }
    }

    /**
     * Non-clustered
     *
     * @param serviceName
     * @return
     */

    @Override
    public InetSocketAddress discovery(String serviceName) {
        try {
            logger.info(serviceName);
            List<Instance> instances = namingService.getAllInstances(serviceName);
            Instance instance = instances.get(0);
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        } catch (Exception e) {
            throw new RpcDiscoveryException(" <NACOS> server discovery fail");
        }
    }

    @Override
    public void destroy(String serviceName) {

    }
}
