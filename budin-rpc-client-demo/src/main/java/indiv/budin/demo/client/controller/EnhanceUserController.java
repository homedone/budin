package indiv.budin.demo.client.controller;

import indiv.budin.aop.annotations.AopAspect;
import indiv.budin.aop.annotations.AopBackward;
import indiv.budin.aop.annotations.AopForward;
import indiv.budin.aop.annotations.AopPointCut;
import indiv.budin.ioc.annotations.IocComponent;

/**
 * @author
 * @date 2023/2/25 19 44
 * discription
 */
@AopAspect
@IocComponent
public class EnhanceUserController {
    @AopPointCut(value = "indiv.budin.demo.client.controller.UserController.getUserInfo",name = "pointcut")
    public void pointCut(){

    }
    @AopForward(execution = "pointcut")
    public void before(){
        System.out.println("before before before before");
    }
    @AopBackward(execution = "pointcut")
    public void after(){
        System.out.println("after after after");
    }
}
