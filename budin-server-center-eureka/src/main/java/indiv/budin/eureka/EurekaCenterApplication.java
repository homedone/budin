package indiv.budin.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author
 * @date 2022/11/6 14 22
 * discription
 */
@EnableEurekaServer
//@EnableAutoConfiguration
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class EurekaCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(EurekaCenterApplication.class, args);
    }
}
