package indiv.budin.ioc.applications;

import indiv.budin.ioc.annotations.IocApplication;
import indiv.budin.ioc.constants.ExceptionMessage;
import indiv.budin.ioc.containers.AnnotationContainer;
import indiv.budin.ioc.containers.AnnotationDependencyInjector;
import indiv.budin.ioc.containers.DependencyInjector;
import indiv.budin.ioc.containers.IocContainer;
import indiv.budin.ioc.exceptions.ApplicationRunException;
import indiv.budin.ioc.simple.controller.UserController;
import indiv.budin.ioc.test.IocTest;
import indiv.budin.ioc.utils.PackageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * @author
 * @date 2022/11/16 17 26
 * discription
 */
@IocApplication
public class BudinIocApplication {
    static Logger logger = LoggerFactory.getLogger(BudinIocApplication.class);


    public static void run(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(IocApplication.class))
            throw new ApplicationRunException(ExceptionMessage.APPLICATION_RUN_EXCEPTION);
        String scanPath = clazz.getPackage().getName();
        Set<Class<?>> packageClass = PackageUtil.getPackageClass(scanPath);
        DependencyInjector annotationDependencyInjector = AnnotationDependencyInjector.creator().scan(packageClass).inject();
        IocContainer iocContainer= annotationDependencyInjector.getIocContainer();
//        for (String key :
//                iocContainer.getBeanContainer().keySet()) {
//            Object obj=iocContainer.getBean(key);
//            logger.info(obj.toString());
//            if (obj instanceof UserController){
//                UserController userController=(UserController) obj;
//                logger.info(userController.getUserService().toString());
//            }
//        }
    }

}
