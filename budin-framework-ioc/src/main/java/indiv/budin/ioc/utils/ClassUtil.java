package indiv.budin.ioc.utils;
import java.lang.reflect.Field;

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
    public static Object stringToNullableTarget(String string, Class<?> clazz) throws Exception {
        return string == null ? null : clazz.getConstructor(String.class).newInstance(string);
    }
    public static Object stringToTarget(String string, Class<?> clazz) {
        boolean nullOrEmpty = StringUtil.isEmpty(string);
        try {
            if(String.class.equals(clazz)){
                return string;
            }
            else if (double.class.equals(clazz)) {
                return nullOrEmpty ? 0 : Double.parseDouble(string);
            } else if (long.class.equals(clazz)) {
                return nullOrEmpty ? 0 : Long.parseLong(string);
            } else if (int.class.equals(clazz)) {
                return nullOrEmpty ? 0 : Integer.parseInt(string);
            } else if (float.class.equals(clazz)) {
                return nullOrEmpty ? 0 : Float.parseFloat(string);
            } else if (short.class.equals(clazz)) {
                return nullOrEmpty ? 0 : Short.parseShort(string);
            } else if (boolean.class.equals(clazz)) {
                return nullOrEmpty;
            }  else if (Number.class.isAssignableFrom(clazz)) {
                return clazz.getConstructor(String.class).newInstance(nullOrEmpty?"0":string);
            }    else {
                return nullOrEmpty ? "" : clazz.getConstructor(String.class).newInstance(string);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
