package indiv.budin.ioc.utils;

/**
 * @author budin
 * @date 2022/11/15 17 21
 * discription
 */
public class StringUtil {
    /**
     * 首字母转大写
     * @param
     * @return
     */

    public static String getMethodName(String fieldName,int pos) {
        try {
            if (pos<0 || pos>=fieldName.length()) return fieldName;
            char[] chars = fieldName.toCharArray();
            chars[pos] = toUpperCase(chars[pos]);
            return String.valueOf(chars);
        }catch (Exception e){
            e.printStackTrace();
        }
        return fieldName;
    }

    public static boolean isPrefixMethod(String methodName,String prefix){
        return methodName.length()>prefix.length() && prefix.equals(methodName.substring(0,prefix.length()));
    }
    public static String getPrefixMethod(String methodAllName,String prefix){
        String[] nameSplit = methodAllName.split("\\.");
        String paramClassName = nameSplit[nameSplit.length - 1];
        return prefix + paramClassName;
    }

    public static char toUpperCase(char c) {
        if (97 <= c && c <= 122) {
            c ^= 32;
        }
        return c;
    }
}
