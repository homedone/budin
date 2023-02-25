package indiv.budin.demo.server;

import indiv.budin.ioc.containers.IocContainer;
import indiv.budin.ioc.utils.PackageUtil;
import indiv.budin.ioc.utils.YamlUtil;
import indiv.budin.rpc.irpc.center.base.ServiceCenter;
import indiv.budin.rpc.irpc.center.nacos.NacosServiceCenter;
import indiv.budin.rpc.irpc.client.base.Client;
import indiv.budin.rpc.irpc.common.utils.FactoryUtil;
import indiv.budin.rpc.irpc.commu.nio.netty.NettyClient;
import indiv.budin.rpc.irpc.commu.nio.netty.NettyServer;
import indiv.budin.rpc.irpc.ioc.RpcProxyDependencyInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

/**
 * @author
 * @date 2023/2/23 20 20
 * discription
 */
public class ApplicationNodeA {
    Logger logger= LoggerFactory.getLogger(ApplicationNodeA.class);
    public static void main(String[] args) {
        Class<?> clazz= ApplicationNodeA.class;
        //扫包
        String scanPath = clazz.getPackage().getName();
        Set<Class<?>> packageClass = PackageUtil.getPackageClass(scanPath);
        Map<String, Object> yamlMap = YamlUtil.getYamlMap(clazz, "application1.yml");
        Map ip = YamlUtil.getObjectMapByPrefix(yamlMap, "budin.server");
        String host=String.valueOf(ip.get("host"));
        Integer port=(Integer) ip.get("port");
        ServiceCenter serviceCenter=(NacosServiceCenter) FactoryUtil.getSingletonInstance(NacosServiceCenter.class);
        Client client=new NettyClient();

        //代理容器注入器
        RpcProxyDependencyInjector rpcProxyDependencyInjector = RpcProxyDependencyInjector.creator().config(yamlMap).scan(packageClass).injectClientProxy(client).inject();
        rpcProxyDependencyInjector.injectToCenter(serviceCenter);
        System.out.println("Netty Server is running! Ip is "+host+"::"+port);
        new NettyServer().run(host,port);

    }
}
