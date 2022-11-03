package indiv.budin.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author
 * @date 2022/11/3 16 43
 * discription
 */
@EnableZuulProxy
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class BudinGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(BudinGatewayApplication.class, args);
    }
}
