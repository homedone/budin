package indiv.budin.demo.client;

import indiv.budin.demo.client.controller.UserController;
import indiv.budin.ioc.utils.PackageUtil;
import indiv.budin.ioc.utils.YamlUtil;
import indiv.budin.rpc.irpc.annotation.RpcApplication;
import indiv.budin.rpc.irpc.annotation.RpcClientApplication;
import indiv.budin.rpc.irpc.client.base.Client;
import indiv.budin.rpc.irpc.commu.nio.netty.NettyClient;
import indiv.budin.rpc.irpc.ioc.BudinRpcClientApplication;
import indiv.budin.rpc.irpc.ioc.RpcProxyContainer;
import indiv.budin.rpc.irpc.ioc.RpcProxyDependencyInjector;
import java.util.Map;
import java.util.Set;

/**
 * @author
 * @date 2023/2/20 12 21
 * discription
 */
@RpcClientApplication
public class ClientApplication {
    public static void main(String[] args) {
        BudinRpcClientApplication.run(ClientApplication.class);
    }
}
