package indiv.budin.demo.server.api;

import org.springframework.boot.SpringApplication;

/**
 * @author
 * @date 2023/2/20 14 40
 * discription
 */
public class NacosApplication {
    public static void main(String[] args) {
        String cmd = "cmd /k start D:\\nacos\\bin\\startup.cmd -m standalone";
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (Exception e) {

        }
    }
}
