package indiv.budin.rpc.irpc.balance;

import indiv.budin.rpc.irpc.carrier.RpcRequest;
import indiv.budin.rpc.irpc.center.base.ServiceCenter;
import indiv.budin.rpc.irpc.center.nacos.NacosServiceCenter;
import indiv.budin.rpc.irpc.common.utils.FactoryUtil;

import java.net.InetSocketAddress;
import java.util.List;

public class ConsistentHashLoadBalancer implements LoadBalancer {
    private final ServiceCenter serviceCenter;

    public ConsistentHashLoadBalancer() {
        serviceCenter = (ServiceCenter) FactoryUtil.getSingletonInstance(NacosServiceCenter.class);
    }

    @Override
    public InetSocketAddress select(List<String> nodeList, RpcRequest request) {

        return null;
    }
}
