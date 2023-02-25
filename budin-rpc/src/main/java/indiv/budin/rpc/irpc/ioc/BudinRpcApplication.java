package indiv.budin.rpc.irpc.ioc;

import indiv.budin.ioc.constants.ExceptionMessage;
import indiv.budin.ioc.exceptions.ApplicationRunException;
import indiv.budin.ioc.utils.PackageUtil;
import indiv.budin.ioc.utils.YamlUtil;
import indiv.budin.rpc.irpc.annotation.RpcApplication;
import indiv.budin.rpc.irpc.annotation.RpcClientApplication;
import indiv.budin.rpc.irpc.center.base.ServiceCenter;
import indiv.budin.rpc.irpc.center.nacos.NacosServiceCenter;
import indiv.budin.rpc.irpc.common.utils.FactoryUtil;
import indiv.budin.rpc.irpc.client.base.Client;
import indiv.budin.rpc.irpc.commu.nio.netty.NettyClient;
import indiv.budin.rpc.irpc.commu.nio.netty.NettyServer;
import indiv.budin.rpc.irpc.server.base.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

/**
 * @author
 * @date 2023/2/25 11 55
 * discription
 */
public class BudinRpcApplication {
    private static Logger logger = LoggerFactory.getLogger(BudinRpcApplication.class);

    private final ServiceCenter serviceCenter;
    private final Client client;
    private final Server server;

    private final Map<String, Object> yamlMap;

    public Map<String, Object> getYamlMap() {
        return yamlMap;
    }

    public BudinRpcApplication(Class<?> clazz) {
        String scanPath = clazz.getPackage().getName();
        Set<Class<?>> packageClass = PackageUtil.getPackageClass(scanPath);
        yamlMap = YamlUtil.getYamlMap(clazz, "application.yml");
        Map ip = YamlUtil.getObjectMapByPrefix(yamlMap, "budin.server");
        Map centerIp = YamlUtil.getObjectMapByPrefix(yamlMap, "budin.center");
        if (ip == null) {
            throw new ApplicationRunException("Application run fail! Ip address not null");
        }
        if (centerIp == null) {
            serviceCenter = (NacosServiceCenter) FactoryUtil.getSingletonInstance(NacosServiceCenter.class);
        } else {
            String address = centerIp.get("host") + ":" + centerIp.get("port");
            serviceCenter = (NacosServiceCenter) FactoryUtil.getSingletonInstance(NacosServiceCenter.class, new Object[]{address});
        }
        String host = String.valueOf(ip.get("host"));
        Integer port = (Integer) ip.get("port");
        client = new NettyClient();
        RpcProxyDependencyInjector.creator().config(yamlMap).scan(packageClass).injectClientProxy(client).inject().injectToCenter(serviceCenter);
        server = new NettyServer(host, port);
    }

    public void run() {
        logger.info("Server is running! Ip is " + server.getAddress());
        server.run();
    }

    public static BudinRpcApplication init(Class<?> clazz){
        if (!clazz.isAnnotationPresent(RpcApplication.class) && !clazz.isAnnotationPresent(RpcClientApplication.class)) {
            throw new ApplicationRunException(ExceptionMessage.APPLICATION_RUN_EXCEPTION);
        }
        return new BudinRpcApplication(clazz);
    }


    public static void run(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(RpcApplication.class)) {
            throw new ApplicationRunException(ExceptionMessage.APPLICATION_RUN_EXCEPTION);
        }
        init(clazz).run();
    }
}
