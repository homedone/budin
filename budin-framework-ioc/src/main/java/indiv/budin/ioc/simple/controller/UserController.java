package indiv.budin.ioc.simple.controller;

import indiv.budin.ioc.annotations.IocAutowired;
import indiv.budin.ioc.annotations.IocController;
import indiv.budin.ioc.simple.service.api.UserService;
import indiv.budin.ioc.simple.entity.Address;
import indiv.budin.ioc.simple.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author
 * @date 2022/11/16 19 16
 * discription
 */

@Data
@IocController
public class UserController {
    @IocAutowired
    private UserService userService;
    public void register(String userName, String password, Address address){
        User user = new User(address, userName, password);
        userService.addUser(user);
    }
    public User getUser(String userName){
        return userService.getUser(userName);
    }
}
