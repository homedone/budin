package indiv.budin.ioc.web;

import indiv.budin.ioc.annotations.IocConfiguration;
import indiv.budin.ioc.containers.AnnotationContainer;
import indiv.budin.ioc.containers.IocContainer;
import indiv.budin.ioc.utils.YamlUtil;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.util.Map;
import java.util.Properties;

/**
 * @author
 * @date 2022/11/22 16 04
 * discription
 */
public class TomcatServer {

    private String name;

    private IocContainer iocContainer;
    Tomcat tomcat;

    public TomcatServer() {
        this.tomcat = new Tomcat();
        iocContainer = AnnotationContainer.getInstance();
    }


    public static TomcatServer create() {
        return new TomcatServer();
    }

    public TomcatServer config(Map config) {
        tomcat.setHostname(String.valueOf(config.get("host")));
        tomcat.setPort((Integer) config.get("port"));
        name = String.valueOf(config.get("name"));
        return this;
    }


    public TomcatServer setPort(int port) {
        tomcat.setPort(port);
        return this;
    }

    public void build() {
        try {
            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            e.printStackTrace();
        }
    }
    /**
     * 将容器中的controller取出，对每个有RequestMapping注解的方法生成对应的servlet
     */
    private void getServlets(){
        for (Class<?> clazz : iocContainer.getAllClasses()) {

        }

    }
}
