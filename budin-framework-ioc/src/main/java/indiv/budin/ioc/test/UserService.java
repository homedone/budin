package indiv.budin.ioc.test;


import indiv.budin.ioc.annotations.IocScan;
import lombok.Data;
import org.springframework.context.annotation.Configuration;

/**
 * @author
 * @date 2022/11/14 18 25
 * discription
 */
@IocScan("indiv.budin.ioc.test")
@Data
@Configuration
public class UserService {
    public UserService() {
    }
    User user;

    User getUserSingleton(){
        return new User(new Address(),"lyh","lyha!");
    }
}
