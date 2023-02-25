package indiv.budin.rpc.irpc.ioc;

import indiv.budin.ioc.containers.ProxyContainer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RpcProxyContainer implements ProxyContainer {
    private final Map<String, Object> container;


    public RpcProxyContainer() {
        this.container = new ConcurrentHashMap<>();
    }


    @Override
    public boolean containsBean(String name) {
        return container.containsKey(name);
    }

    @Override
    public void put(String name, Object bean) {
        container.put(name, bean);
    }

    @Override
    public void doProxy() {

    }

    @Override
    public Object getBean(String s) {
        if (container.containsKey(s)) return container.get(s);
        return null;
    }

}
