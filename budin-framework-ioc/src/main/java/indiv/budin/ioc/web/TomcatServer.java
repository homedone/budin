package indiv.budin.ioc.web;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.startup.Tomcat;

import javax.servlet.Servlet;
import java.util.Map;
import java.util.Properties;

/**
 * @author
 * @date 2022/11/22 16 04
 * discription
 */
public class TomcatServer {

    private String name;

    Tomcat tomcat;

    public TomcatServer() {
        this.tomcat = new Tomcat();
    }


    public static TomcatServer create() {
        return new TomcatServer();
    }

    public TomcatServer config(Map config) {
        tomcat.setBaseDir(String.valueOf(config.get("baseDir")));
        tomcat.setHostname(String.valueOf(config.get("host")));
        tomcat.setPort((Integer) config.get("port"));
        name = String.valueOf(config.get("name"));
        tomcat.getConnector();

        String contextPath = "";
        if (config.containsKey("context")) {
            contextPath = String.valueOf(config.get("context"));
        }
        String docBase = "";
        if (config.containsKey("docBase")) {
            docBase = String.valueOf(config.get("docBase"));
        }
        return this;
    }

    public TomcatServer addServlet(String servletName, Servlet servlet) {
        Context context = tomcat.addContext("", "/..");
//        Tomcat.initWebappDefaults(context);
        tomcat.addServlet("", servletName, servlet);

        context.addServletMappingDecoded("/*",servletName);

//        context.addServletMappingDecoded("/*",servletName);
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

}
