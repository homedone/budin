package indiv.budin.demo.server;

import indiv.budin.ioc.containers.DependencyInjector;
import indiv.budin.ioc.containers.IocContainer;
import indiv.budin.ioc.utils.PackageUtil;
import indiv.budin.ioc.utils.YamlUtil;
import indiv.budin.rpc.irpc.annotation.RpcApplication;
import indiv.budin.rpc.irpc.commu.nio.netty.NettyServer;
import indiv.budin.rpc.irpc.ioc.BudinRpcApplication;
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
@RpcApplication
public class Application {
    Logger logger=LoggerFactory.getLogger(Application.class);
    public static void main(String[] args) {
        BudinRpcApplication.run(Application.class);
    }
}
