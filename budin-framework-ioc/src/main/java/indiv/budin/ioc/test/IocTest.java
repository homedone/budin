package indiv.budin.ioc.test;

import indiv.budin.ioc.containers.AnnotationContainer;
import indiv.budin.ioc.containers.AnnotationDependencyInjector;
import indiv.budin.ioc.containers.IocContainer;
import indiv.budin.ioc.utils.PackageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

/**
 * @author
 * @date 2022/11/14 20 03
 * discription
 */
public class IocTest {
    static Logger logger = LoggerFactory.getLogger(IocTest.class);

    /**
     * 测试，如果没有值，就会抛出 NoScanException
     */
    public static void testAnnotation() {
        AnnotationDependencyInjector annotationDependencyInjector = new AnnotationDependencyInjector();
        IocContainer iocContainer=annotationDependencyInjector.getIocContainer();
        iocContainer.iocScan(UserService.class);
        User user = (User) iocContainer.getBean(User.class.getName());
        //这里没有注入，Address is null
        if (user.getAddress()!=null)
            logger.info(user.getAddress().toString());
        annotationDependencyInjector.setAttributionByAutowired(User.class);
        logger.info(user.introduce());
        //这里属性注入，not null
        logger.info(user.getAddress().toString());
    }

    public static void scanPackage() {
        Set<Class<?>> packageClass = PackageUtil.getPackageClass("indiv.budin.ioc.exceptions");
        for (Class<?> clazz :packageClass) {
            logger.info(clazz.getName());
        }
    }

    public static void main(String[] args) {
        testAnnotation();
//        scanPackage();
    }
}
