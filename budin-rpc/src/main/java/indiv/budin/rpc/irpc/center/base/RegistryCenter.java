package indiv.budin.rpc.irpc.center.base;

import java.net.InetSocketAddress;

public interface RegistryCenter {
    /**
     * 注册服务
     * @param serviceName
     * @param inetSocketAddress
     */
    void register(String serviceName, InetSocketAddress inetSocketAddress);

    /**
     * 发现服务
     * @param serviceName
     * @return
     */

    InetSocketAddress discovery(String serviceName);

    InetSocketAddress discovery(String serviceName,String requestKey);

    /**
     * 销毁一个服务
     * @param serviceName
     */
    void destroy(String serviceName);
}
