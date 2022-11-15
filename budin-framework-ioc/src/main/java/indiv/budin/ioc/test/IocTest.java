package indiv.budin.ioc.test;

import indiv.budin.ioc.annotations.IocBean;
import indiv.budin.ioc.containers.AnnotationContainer;
import indiv.budin.ioc.containers.IocContainer;
import indiv.budin.ioc.utils.PackageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
        AnnotationContainer iocContainer = new AnnotationContainer();
        iocContainer.iocScan(UserService.class);
        User user = (User) iocContainer.getBean(User.class.getName());
        iocContainer.setAttributionByFieldAnnotation(User.class, IocBean.class);
        logger.info(user.introduce());
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
