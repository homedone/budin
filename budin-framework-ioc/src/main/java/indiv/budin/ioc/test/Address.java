package indiv.budin.ioc.test;

import indiv.budin.ioc.annotations.IocService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author
 * @date 2022/11/15 20 19
 * discription
 */
@IocService
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Address {
    private String province;
    private String municipal;
    private String township;

}
