package indiv.budin.rpc.irpc.balance;

import indiv.budin.rpc.irpc.carrier.RpcRequest;

import java.net.InetSocketAddress;
import java.util.List;

public interface LoadBalancer {
    InetSocketAddress select(List<String> nodeList, RpcRequest request);
}
