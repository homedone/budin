package indiv.budin.ioc.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author
 * @date 2022/11/16 15 22
 * discription
 */
public class ClassUtil {
    public static void setAttribute(Field field,Object clazzObj,Object obj){
        try {
            //只有属性为空时才注入
            if (field.get(clazzObj)==null) field.set(clazzObj, obj);
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
    }
}
