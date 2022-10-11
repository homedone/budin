package indiv.budin.common.utils;
import io.minio.BucketExistsArgs;

/**
 * @author
 * @date 2022/10/11 17 30
 * discription
 */
public class OSSUtil {
    public Boolean bucketExists(String bucketName) {
        Boolean found;
        try {
            found = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return found;
    }
}
