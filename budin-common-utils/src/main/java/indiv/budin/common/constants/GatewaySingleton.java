package indiv.budin.common.constants;

/**
 * @author
 * @date 2022/11/6 20 32
 * discription
 */
public class GatewaySingleton {
    private static volatile GatewaySingleton gateway;

    private String secrete;

    private GatewaySingleton() {

    }

    public static GatewaySingleton getGateway() {
        if (gateway == null) {
            synchronized (GatewaySingleton.class) {
                if (gateway == null) {
                    gateway = new GatewaySingleton();
                }
            }
        }
        return gateway;
    }

    public void setSecrete(String secrete) {
        this.secrete = secrete;
    }

    public String getSecrete() {
        return secrete;
    }
}
