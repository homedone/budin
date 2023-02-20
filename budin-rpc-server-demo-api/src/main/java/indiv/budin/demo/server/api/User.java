package indiv.budin.demo.server.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author
 * @date 2022/11/14 17 34
 * discription
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private String address;
    private String userName;
    private String password;

    public String introduce() {
        return "Hello! I am " + getUserName()+" ,I am from "+address;
    }


}



