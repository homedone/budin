package indiv.budin.rpc.irpc.ioc;

import indiv.budin.ioc.containers.WebProxyContainer;
import indiv.budin.ioc.exceptions.ApplicationRunException;
import indiv.budin.ioc.utils.YamlUtil;
import indiv.budin.ioc.web.TomcatServer;
import indiv.budin.rpc.irpc.annotation.RpcClientApplication;

import javax.servlet.http.HttpServlet;
import java.util.Map;

/**
 * @author
 * @date 2023/2/25 16 48
 * discription
 */

public class BudinRpcClientApplication {
    public static void run(Class<?> clazz){
        if (!clazz.isAnnotationPresent(RpcClientApplication.class)) {
            throw new ApplicationRunException("Application run fail, check with "+RpcClientApplication.class.getName());
        }
        BudinRpcApplication budinRpcApplication = BudinRpcApplication.init(clazz);
        Map tomcatConfig = YamlUtil.getObjectMapByPrefix(budinRpcApplication.getYamlMap(), "budin.server");
        String baseDir = clazz.getProtectionDomain().getCodeSource().getLocation().getPath();
        tomcatConfig.put("baseDir", baseDir.substring(1,baseDir.length()-1));
        HttpServlet webServlet = WebProxyContainer.create().build();
        TomcatServer.create().config(tomcatConfig).addServlet(clazz.getName(), webServlet).build();
    }
}
