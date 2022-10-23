package indiv.budin.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 文件上传下载工具类
 */
public class FileUtil {
    public static long getUnit(String unit) {
        if ("MB".equals(unit)) return 1024 * 1024;
        if ("GB".equals(unit)) return 1024 * 1024 * 1024;
        if ("KB".equals(unit)) return 1024;
        return 1024;
    }

}
