package indiv.budin.ioc.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * @author
 * @date 2022/11/14 17 23
 * discription
 */
public class SpringTest {
    static Logger logger = LoggerFactory.getLogger(SpringTest.class);

    public static void testXml() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("user.xml");
        User user = (User) applicationContext.getBean("user1");
        logger.info("Xml way: " + user.introduce());
        logger.info(user.toString());
    }

    public static void testAnnotation() {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(UserService.class);
        User user =(User) applicationContext.getBean("lyh");
        logger.info("Annotation way: " + user.introduce());
        logger.info(user.toString());
    }

    public static void testAutowire(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("user.xml");
        UserService userService =(UserService) applicationContext.getBean("userService");
        logger.info("Annotation way: " + userService.user.introduce());
        logger.info(userService.user.toString());
    }

    public static void main(String[] args) {
//        testXml();
//        testAnnotation();
        testAutowire();

    }
}
