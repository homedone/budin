package indiv.budin.demo.client.controller;

import indiv.budin.common.utils.ResultUtil;
import indiv.budin.demo.server.api.People;
import indiv.budin.demo.server.api.UserService;
import indiv.budin.ioc.annotations.*;
import indiv.budin.rpc.irpc.annotation.RpcAutowire;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * @author
 * @date 2023/2/20 11 29
 * discription
 */
@IocController
public class UserController {

    static Logger logger = LoggerFactory.getLogger(UserController.class);
    @RpcAutowire(serviceName = "indiv.budin.demo.server.service.UserService", version = "1.0", node = "1.0")
    UserService userService;

    /**
     * test json
     * Post http://host:port/get/user/info?userName=xxx
     * {
     * "people":{
     * "sex": "xxx",
     * "address":{
     * "province": "xxx",
     * "city": "xxx"
     * }
     * }
     * }
     *
     * @param userName
     * @return
     */
    @IocRequestMapping(url = "/get/user/info")
    @IocResponseBody
    public ResultUtil<String> getUserInfo(@IocRequestParam(value = "userName") String userName, @IocRequestBody People people) {
        System.out.println("------准备获取用户介绍------");
        for (int i = 0; i < 1000; i++) {
            int k = new Random().nextInt(100);
            String name = "name-" + k;
            String introduce = userService.getUserIntroduce(name);
            logger.info(introduce);
        }
        String s = userService.getUserIntroduce(userName);
        System.out.println("用户信息： " + people.toString());
        System.out.println("------用户开始介绍 -------");
        System.out.println("--------用户介绍完成--------");
        return ResultUtil.successWithoutData();
    }
}

