package indiv.budin.demo.client;

import indiv.budin.ioc.containers.WebProxyContainer;
import indiv.budin.ioc.exceptions.ApplicationRunException;
import indiv.budin.ioc.utils.PackageUtil;
import indiv.budin.ioc.utils.YamlUtil;
import indiv.budin.ioc.web.TomcatServer;
import indiv.budin.rpc.irpc.center.nacos.NacosServiceCenter;
import indiv.budin.rpc.irpc.client.base.Client;
import indiv.budin.rpc.irpc.common.utils.FactoryUtil;
import indiv.budin.rpc.irpc.commu.nio.netty.NettyClient;
import indiv.budin.rpc.irpc.commu.nio.netty.NettyServer;
import indiv.budin.rpc.irpc.ioc.BudinRpcApplication;
import indiv.budin.rpc.irpc.ioc.RpcProxyDependencyInjector;
import indiv.budin.rpc.irpc.server.base.Server;

import javax.servlet.http.HttpServlet;
import java.util.Map;
import java.util.Set;

public class ClientApplication1 {
    public static void main(String[] args) {
        Class clazz = ClientApplication1.class;
        String scanPath = clazz.getPackage().getName();
        Set<Class<?>> packageClass = PackageUtil.getPackageClass(scanPath);
        Map yamlMap = YamlUtil.getYamlMap(clazz, "application1.yml");
        Map ip = YamlUtil.getObjectMapByPrefix(yamlMap, "budin.server");
        Map centerIp = YamlUtil.getObjectMapByPrefix(yamlMap, "budin.center");
        if (ip == null) {
            throw new ApplicationRunException("Application run fail! Ip address not null");
        }
        NacosServiceCenter serviceCenter;
        if (centerIp == null) {
            serviceCenter = (NacosServiceCenter) FactoryUtil.getSingletonInstance(NacosServiceCenter.class);
        } else {
            String address = centerIp.get("host") + ":" + centerIp.get("port");
            serviceCenter = (NacosServiceCenter) FactoryUtil.getSingletonInstance(NacosServiceCenter.class, new Object[]{address});
        }
        String host = String.valueOf(ip.get("host"));
        Integer port = (Integer) ip.get("port");
        Client client = new NettyClient();
        RpcProxyDependencyInjector.creator().config(yamlMap).scan(packageClass).injectClientProxy(client).inject().injectToCenter(serviceCenter);
        Server server = new NettyServer(host, port);
        Map tomcatConfig = YamlUtil.getObjectMapByPrefix(yamlMap, "budin.server");
        String baseDir = clazz.getProtectionDomain().getCodeSource().getLocation().getPath();
        tomcatConfig.put("baseDir", baseDir.substring(1, baseDir.length() - 1));
        HttpServlet webServlet = WebProxyContainer.create().build();
        TomcatServer.create().config(tomcatConfig).addServlet(clazz.getName(), webServlet).build();

    }
}
