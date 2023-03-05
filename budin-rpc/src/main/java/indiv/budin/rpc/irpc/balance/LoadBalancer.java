package indiv.budin.rpc.irpc.balance;

import indiv.budin.rpc.irpc.carrier.RpcRequest;

import java.net.InetSocketAddress;
import java.util.List;

public interface LoadBalancer<T> {
    T select(List<T> nodeList, RpcRequest request);

    T select(RpcRequest request);

    T select(String serviceName,String requestKey);

    void putInto(List<T> nodeList,String serviceName);

    void consistent(List<String> nodeList, String serviceName);

    boolean contains(String serviceName);
}
