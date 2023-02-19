package indiv.budin.rpc.irpc.common.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

/**
 * 一个工厂方法，包括单例工厂
 */
public class FactoryUtil {

    private static final byte[] lock=new byte[]{};
    private static final Map<String,Object> singletonMap=new ConcurrentHashMap<>();

    public static Object getSingletonInstance(Class<?> clazz) {
        String singletonName = clazz.getName();
        if (!singletonMap.containsKey(singletonName)) {
            try {
                synchronized (lock) {
                    if (!singletonMap.containsKey(singletonName)) {
                        singletonMap.put(singletonName, clazz.newInstance());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return singletonMap.get(singletonName);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                Cat cat=(Cat) FactoryUtil.getSingletonInstance(Cat.class);
                System.out.println("线程 "+Thread.currentThread().getName()+" 获取了cat对象: "+cat.toString());
            }).start();
        }
    }
}
class Cat{
    public Cat() {

    }

    String introduce(){
        return "i am a cat";
    }
}


