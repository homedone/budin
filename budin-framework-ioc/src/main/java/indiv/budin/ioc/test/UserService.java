package indiv.budin.ioc.test;


import indiv.budin.ioc.annotations.IocScan;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
    @Autowired
    User user;

    User getUserSingleton(){
        return new User("lyh","lyha!");
    }
}
