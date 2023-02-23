package indiv.budin.demo.client;

import indiv.budin.demo.client.controller.UserController;
import indiv.budin.ioc.utils.PackageUtil;
import indiv.budin.ioc.utils.YamlUtil;
import indiv.budin.rpc.irpc.ioc.RpcProxyContainer;
import indiv.budin.rpc.irpc.ioc.RpcProxyDependencyInjector;
import java.util.Map;
import java.util.Set;

/**
 * @author
 * @date 2023/2/20 12 21
 * discription
 */
public class ClientApplication {
    public static void main(String[] args) {
        Class<?> clazz= ClientApplication.class;
        //扫包
        String scanPath = clazz.getPackage().getName();
        Set<Class<?>> packageClass = PackageUtil.getPackageClass(scanPath);
        Map<String, Object> yamlMap = YamlUtil.getYamlMap(clazz, "application.yml");
        //代理容器注入器
        RpcProxyDependencyInjector rpcProxyDependencyInjector = (RpcProxyDependencyInjector) RpcProxyDependencyInjector.creator();
        rpcProxyDependencyInjector.config(yamlMap).scan(packageClass).inject();
        UserController userController = (UserController) rpcProxyDependencyInjector.getBean(UserController.class.getName());
        for (int i = 0; i < 100; i++) {
            userController.getUserInfo("dxq");
        }
    }
}
