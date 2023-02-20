package indiv.budin.demo.client.controller;

import indiv.budin.demo.server.api.UserService;
import indiv.budin.ioc.annotations.IocController;
import indiv.budin.rpc.irpc.annotation.RpcAutowire;

/**
 * @author
 * @date 2023/2/20 11 29
 * discription
 */
@IocController
public class UserController {
    @RpcAutowire(serviceName = "indiv.budin.demo.server.service.UserService",version = "1.0", node = "1.0")
    UserService userService;
    public void getUserInfo(String userName){
        System.out.println("------准备获取用户介绍------");
        String s = userService.getUserIntroduce(userName);
        System.out.println("------用户开始介绍 -------");
        System.out.println(s);
        System.out.println("--------用户介绍完成--------");
    }
}
