package indiv.budin.fileservice;

import indiv.budin.fileservice.utils.OSSUtil;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FileServiceApplication.class)
public class OssUtilTest {
    @Autowired
    private OSSUtil ossUtil;

    private Logger logger = LoggerFactory.getLogger(FileServiceMysqlTest.class);

    //test ok
    @Test
    public void bucketTest() {
        String bucketName = "budin-oss2";
        if (!ossUtil.bucketExists(bucketName))
            ossUtil.makeBucket(bucketName);
        else
            logger.info("oss已经存在");
    }

    //test ok
    @Test
    public void getAllBucketTest() {
        List<Bucket> allBuckets = ossUtil.getAllBuckets();
        for (Bucket bucket : allBuckets) {
            logger.info(bucket.name());
        }
    }

    //test ok
    @Test
    public void createDirectoryTest() {
        try {
            boolean res = ossUtil.createDirectory("budin-oss1", "/dxq/ook/");
            logger.info(Boolean.toString(res));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //test ok
    @Test
    public void previewTest() {
        String url = ossUtil.preview("budin-oss1", "dxq");
        logger.info(url);
    }

    //test ok
    @Test
    public void checkFolderIsExistTest() {
        boolean res = ossUtil.checkFolderIsExist("budin-oss1", "/dxq/ook");
        logger.info(Boolean.toString(res));
    }

    //test ok
    @Test
    public void listObjectsTest() {
        for (Item listObject : ossUtil.listObjects("budin-oss1")) {
            logger.info(listObject.objectName());
        }
    }

    //test ok
    @Test
    public void removeTest() {
        boolean res = ossUtil.remove("budin-oss1", "dxq/");
        logger.info(Boolean.toString(res));
    }

}
