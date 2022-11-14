package indiv.budin.ioc.utils;

import indiv.budin.ioc.constants.ExceptionMessage;
import indiv.budin.ioc.exceptions.NotFindClassException;
import indiv.budin.ioc.test.SpringTest;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author
 * @date 2022/11/14 20 13
 * discription
 */
public class PackageUtil {
    static Logger logger = LoggerFactory.getLogger(PackageUtil.class);
    public static final String FILE_PROTOCOL = "file";
    public static final String JAR_PROTOCOL = "jar";


    public static Set<Class<?>> getPackageClass(String packageName) {
        URL url = Thread.currentThread().getContextClassLoader().getResource(packageName.replace(".","/"));
        if (url == null) throw new NotFindClassException(ExceptionMessage.NOT_FIND_CLASS_EXCEPTION);
        Set<Class<?>> set = new HashSet<Class<?>>();
        String protocol = url.getProtocol();
        try {
            if (FILE_PROTOCOL.equals(protocol)) {
                String fileDir = url.getFile();
                File file = new File(fileDir);
                getPackageClassFile(set, file, packageName);
            } else if (JAR_PROTOCOL.equals(protocol)) {
                JarFile jarFile = ((JarURLConnection) url.openConnection()).getJarFile();
                getPackageClassJar(set, jarFile, packageName);
            }
        } catch (IOException ioException){
            ioException.printStackTrace();
        }
        return set;
    }

    /**
     * 递归获取文件下的class
     *
     * @param set
     * @param file
     * @param packageName
     * @throws ClassNotFoundException
     */
    public static void getPackageClassFile(Set<Class<?>> set, File file, String packageName) {
        if (!file.exists() || !file.isDirectory()) return;
        File[] files = file.listFiles();
        if (files == null) return;
        for (File fileSon : files) {
            if (fileSon.isDirectory()) {
                // 递归扫描
                getPackageClassFile(set, fileSon, packageName + "/" + fileSon.getName());
            } else {
                // 是文件并且是以 .class结尾
                try {
                    if (fileSon.getName().endsWith(".class")) {
                        String className = packageName.replace("/", ".") + "." + fileSon.getName().replace(".class", "");
//                        logger.info("正在加载: " + className);
                        set.add(Class.forName(className));
                    }
                }catch (ClassNotFoundException e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 递归获取jar下的class
     *
     * @param set
     * @param jarFile
     * @param packageName
     */
    public static void getPackageClassJar(Set<Class<?>> set, JarFile jarFile, String packageName) {
        String packageDirName = packageName.replace('.', '/');
        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
            JarEntry entry = entries.nextElement();
            String name = entry.getName();
            // 如果是以/开头的
            if (name.charAt(0) == '/') {
                // 获取后面的字符串
                name = name.substring(1);
            }
            // 如果前半部分和定义的包名相同
            if (name.startsWith(packageDirName)) {
                int idx = name.lastIndexOf('/');
                // 如果以"/"结尾 是一个包
                if (idx != -1) {
                    // 获取包名 把"/"替换成"."
                    packageName = name.substring(0, idx).replace('/', '.');
                }
                // 如果可以迭代下去 并且是一个包
                if (idx != -1) {
                    // 如果是一个.class文件 而且不是目录
                    if (name.endsWith(".class") && !entry.isDirectory()) {
                        // 去掉后面的".class" 获取真正的类名
                        String className = packageName.replace("/", ".") + "." + name.replace(".class", "");
                        try {
                            // 添加到classes
                            set.add(Class.forName(packageName + '.' + className));
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}