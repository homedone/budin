package indiv.budin.ioc.simple.controller;

import indiv.budin.ioc.annotations.IocAutowired;
import indiv.budin.ioc.annotations.IocController;
import indiv.budin.ioc.annotations.IocRequestMapping;
import indiv.budin.ioc.annotations.IocRequestParam;
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

@IocController
public class UserController {
    //    @IocAutowired
    private UserService userService;

    @IocRequestMapping(url = "/simple/register")
    public void register(@IocRequestParam(value = "name") String userName, @IocRequestParam(value = "pass") String password) {
        Address address=new Address("jx","px","ay");
        System.out.println("收到");
        User user = new User(address, userName, password);
        userService.addUser(user);
    }

    @IocRequestMapping(url = "/simple/get")
    public User getUser(@IocRequestParam(value = "name") String userName) {
        return userService.getUser(userName);
    }

    @IocAutowired()
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public UserService getUserService() {
        return userService;
    }
}
