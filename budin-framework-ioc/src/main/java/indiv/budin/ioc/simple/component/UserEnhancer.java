package indiv.budin.ioc.simple.component;

import indiv.budin.aop.annotations.AopAspect;
import indiv.budin.aop.annotations.AopBackward;
import indiv.budin.aop.annotations.AopForward;
import indiv.budin.aop.annotations.AopPointCut;
import indiv.budin.ioc.annotations.IocComponent;
import org.slf4j.LoggerFactory;

@AopAspect
@IocComponent
public class UserEnhancer {

    @AopPointCut(value = "indiv.budin.ioc.simple.controller.UserController.register",name = "pointcut")
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
