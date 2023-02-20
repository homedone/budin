package indiv.budin.demo.server;

import indiv.budin.ioc.containers.DependencyInjector;
import indiv.budin.ioc.containers.IocContainer;
import indiv.budin.ioc.utils.PackageUtil;
import indiv.budin.ioc.utils.YamlUtil;
import indiv.budin.rpc.irpc.commu.nio.netty.NettyServer;
import indiv.budin.rpc.irpc.ioc.RpcProxyDependencyInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

/**
 * @author
 * @date 2023/2/20 14 25
 * discription
 */
public class Application {
    Logger logger=LoggerFactory.getLogger(Application.class);
    public static void main(String[] args) {
        Class<?> clazz= Application.class;
        //扫包
        String scanPath = clazz.getPackage().getName();
        Set<Class<?>> packageClass = PackageUtil.getPackageClass(scanPath);
        for (Class<?> c: packageClass) {
            System.out.println(c.getName());
        }
        Map<String, Object> yamlMap = YamlUtil.getYamlMap(clazz, "application.yml");

        //代理容器注入器
        RpcProxyDependencyInjector rpcProxyDependencyInjector = RpcProxyDependencyInjector.creator().config(yamlMap).scan(packageClass).inject();
        IocContainer iocContainer = rpcProxyDependencyInjector.getIocContainer();
        NettyServer nettyServer =new NettyServer();
        Map ip = YamlUtil.getObjectMapByPrefix(yamlMap, "budin.server");
        String host=String.valueOf(ip.get("host"));
        Integer port=(Integer) ip.get("port");
        System.out.println("Netty Server is running! Ip is "+host+"::"+port);
        nettyServer.run(host,port);
    }
}
