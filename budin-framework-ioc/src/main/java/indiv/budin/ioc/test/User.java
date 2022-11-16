package indiv.budin.ioc.test;

import indiv.budin.ioc.annotations.IocBean;
import indiv.budin.ioc.annotations.IocComponent;
import indiv.budin.ioc.annotations.IocScan;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author
 * @date 2022/11/14 17 34
 * discription
 */

@IocComponent
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @IocBean
    private Address address;
    private String userName;
    private String password;

    public String introduce() {
        return "i am a user";
    }
}
