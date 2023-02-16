package indiv.budin.ioc.test;

import indiv.budin.ioc.annotations.IocAutowired;
import indiv.budin.ioc.annotations.IocBean;
import indiv.budin.ioc.annotations.IocComponent;
import indiv.budin.ioc.annotations.IocScan;
import indiv.budin.ioc.test.Address;
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
public class User implements People{

    @IocAutowired
    private Address address;
    private String userName;
    private String password;

    public String introduce() {
        return "i am a user";
    }

    public String pay(int k) {
        return "pay " + k + " yuan";
    }

    @IocAutowired
    public void setAddress(Address address) {
        this.address = address;
    }
}
