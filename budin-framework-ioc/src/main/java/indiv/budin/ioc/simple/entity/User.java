package indiv.budin.ioc.simple.entity;

import com.beust.ah.A;
import indiv.budin.ioc.annotations.IocAutowired;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 * @date 2022/11/14 17 34
 * discription
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @IocAutowired
    private Address address;
    private String userName;
    private String password;

    public String introduce() {
        return "i am a user";
    }


    @IocAutowired
    public void setAddress(Address address){
        this.address=address;
    }

}



