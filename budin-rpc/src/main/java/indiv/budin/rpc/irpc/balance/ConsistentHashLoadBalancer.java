package indiv.budin.rpc.irpc.balance;

import indiv.budin.rpc.irpc.balance.selectors.ConsistentHashSelector;
import indiv.budin.rpc.irpc.balance.selectors.HashSelector;
import indiv.budin.rpc.irpc.carrier.RpcRequest;
import indiv.budin.rpc.irpc.center.base.ServiceCenter;
import indiv.budin.rpc.irpc.center.nacos.NacosServiceCenter;
import indiv.budin.rpc.irpc.common.utils.FactoryUtil;

import java.net.InetSocketAddress;
import java.nio.channels.Selector;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConsistentHashLoadBalancer implements LoadBalancer<String> {

    private Map<String, ConsistentHashSelector> selectorMap;

    public ConsistentHashLoadBalancer() {
        selectorMap = new ConcurrentHashMap<>();
    }

    @Override
    public String select(List<String> nodeList, RpcRequest request) {
        String serviceName = request.getServiceName();
        putInto(nodeList,serviceName);
        return selectorMap.get(serviceName).select(request.getServiceName() + request.getMessageId());
    }
    @Override
    public String select(RpcRequest request) {
        return select(request.getServiceName(), request.getServiceName()+request.getMessageId());
    }
    public String select(String serviceName,String requestKey) {
        if (!selectorMap.containsKey(serviceName)) {
            return null;
        }
        return selectorMap.get(serviceName).select(serviceName+requestKey);
    }

    @Override
    public void putInto(List<String> nodeList, String serviceName) {
        if (!selectorMap.containsKey(serviceName)) {
            selectorMap.put(serviceName, ConsistentHashSelector.createDefault().injectNodes(nodeList));
        }
    }
}
