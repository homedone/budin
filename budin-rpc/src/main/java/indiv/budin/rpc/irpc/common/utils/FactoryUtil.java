package indiv.budin.rpc.irpc.common.utils;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;

/**
 * 一个工厂方法，包括单例工厂
 */
public class FactoryUtil {

    private static final byte[] lock = new byte[]{};
    private static final Map<String, Object> singletonMap = new ConcurrentHashMap<>();


    /**
     * concurrentHashMap的话，已经线程安全了，但还需要进行double check,containsKey+put之后线程不安全
     * 所以下面方法是线程不安全的
     *
     * @param clazz
     * @return
     */
    public static Object getClassSingletonInstance(Class<?> clazz) {
        String singletonName = clazz.getName();
        try {
            Object obj = singletonMap.getOrDefault(singletonName, singletonMap.put(singletonName, clazz.newInstance()));
            return obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

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

    public static Object getSingletonInstance(Class<?> clazz, Object[] parameters) {
        String singletonName = clazz.getName();
        if (!singletonMap.containsKey(singletonName)) {
            try {
                synchronized (lock) {
                    if (!singletonMap.containsKey(singletonName)) {
                        Constructor<?>[] declaredConstructors = clazz.getDeclaredConstructors();
                        Constructor<?> cons = declaredConstructors[0];
                        for (Constructor constructor : declaredConstructors) {
                            int parameterCount = constructor.getParameterCount();
                            if (parameters.length == parameterCount) {
                                cons = constructor;
                                break;
                            }
                        }
                        singletonMap.put(singletonName, cons.newInstance(parameters));
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
            new Thread(() -> {
//                Cat cat = (Cat) FactoryUtil.getSingletonInstance(Cat.class);
                Cat cat = (Cat) FactoryUtil.getSingletonInstance(Cat.class);
                System.out.println("线程 " + Thread.currentThread().getName() + " 获取了cat对象: " + cat);
                Dog dog = (Dog) FactoryUtil.getSingletonInstance(Dog.class);
                System.out.println("线程 " + Thread.currentThread().getName() + " 获取了dog对象: " + dog);
            }).start();
        }
    }
}

class Dog {

}

class Cat {
    public Cat() {

    }

    public Cat(String sex) {
        this.sex = sex;
    }

    String sex;

    String introduce() {
        return "i am a cat, sex is " + this.sex;
    }
}

class People<T> {
    T t;
    int age;

    public People(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public void setT(T t) {
        this.t = t;
    }

    String introduce() {
        return "i am people " + this.t;
    }

    public static void main(String[] args) {
        People<String> people=(People) FactoryUtil.getSingletonInstance(People.class, new Object[]{10});
        people.setT("hhhh");
        System.out.println(people.getAge());
        System.out.println(people.introduce());
    }
}


