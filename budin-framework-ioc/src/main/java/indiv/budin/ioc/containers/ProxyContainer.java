package indiv.budin.ioc.containers;

public interface ProxyContainer {

    Object getBean(String s);

    boolean containsBean(String name);

    void put(String name, Object bean);

    void doProxy();
}
