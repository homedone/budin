package indiv.budin.ioc.simple.entity;

import indiv.budin.ioc.annotations.IocComponent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 * @date 2022/11/15 20 19
 * discription
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Address {
    private String province;
    private String municipal;
    private String township;

}
