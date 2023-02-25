package indiv.budin.ioc.utils;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;

/**
 * @author
 * @date 2022/11/22 19 10
 * discription
 */
public class YamlUtil {

    public static Map<String, Object> getYamlMap(Class<?> clazz, String file) {
        Yaml yaml = new Yaml();
        InputStream inputStream = clazz.getClassLoader().getResourceAsStream(file);
        Map<String, Object> yamlMap = yaml.load(inputStream);
        return yamlMap;
    }

    public static Map getObjectMapByPrefix(Map objectMap, String prefix) {
        String[] split = prefix.split("\\.");
        for (String key : split) {
            if (!objectMap.containsKey(key)) return null;
            objectMap = (Map) objectMap.get(key);
        }
        return objectMap;
    }
}
