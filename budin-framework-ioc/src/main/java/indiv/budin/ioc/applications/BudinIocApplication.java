package indiv.budin.ioc.applications;

import indiv.budin.ioc.annotations.IocApplication;
import indiv.budin.ioc.constants.ExceptionMessage;
import indiv.budin.ioc.containers.*;
import indiv.budin.ioc.exceptions.ApplicationRunException;
import indiv.budin.ioc.utils.PackageUtil;
import indiv.budin.ioc.utils.YamlUtil;
import indiv.budin.ioc.web.TomcatServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import javax.servlet.http.HttpServlet;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

/**
 * @author
 * @date 2022/11/16 17 26
 * discription
 */
public class BudinIocApplication {
    static Logger logger = LoggerFactory.getLogger(BudinIocApplication.class);

    TomcatServer tomcatServer;

    public static void run(Class<?> clazz) {
        BudinIocApplication budinIocApplication = new BudinIocApplication();
        if (!clazz.isAnnotationPresent(IocApplication.class))
            throw new ApplicationRunException(ExceptionMessage.APPLICATION_RUN_EXCEPTION);
        //扫包
        String scanPath = clazz.getPackage().getName();
        Set<Class<?>> packageClass = PackageUtil.getPackageClass(scanPath);
        Map<String, Object> yamlMap = YamlUtil.getYamlMap(clazz, "application.yml");
        //容器bean生成，依赖关系注入
//        DependencyInjector annotationDependencyInjector = AnnotationDependencyInjector.creator().config(yamlMap).scan(packageClass).inject();
        //代理容器注入器
        DependencyInjector annotationDependencyInjector = ProxyDependencyInjector.creator().config(yamlMap).scan(packageClass).inject();
        //servlet容器
//        HttpServlet webServlet = WebContainer.create().build();
        HttpServlet webServlet = WebProxyContainer.create().build();
        //服务配置，web服务器启动
        Map tomcatConfig = YamlUtil.getObjectMapByPrefix(yamlMap, "budin.server");
        String baseDir = clazz.getProtectionDomain().getCodeSource().getLocation().getPath();
        tomcatConfig.put("baseDir", baseDir.substring(1,baseDir.length()-1));
        budinIocApplication.tomcatServer = TomcatServer.create().config(tomcatConfig);
        budinIocApplication.tomcatServer.addServlet(clazz.getName(), webServlet).build();
    }

}
