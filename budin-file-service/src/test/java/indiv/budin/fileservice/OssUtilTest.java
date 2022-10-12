package indiv.budin.fileservice;

import indiv.budin.fileservice.utils.OSSUtil;
import io.minio.messages.Bucket;
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
    public void  getAllBucketTest(){
        List<Bucket> allBuckets = ossUtil.getAllBuckets();
        for(Bucket bucket:allBuckets){
            logger.info(bucket.name());
        }
    }
    //test ok
    @Test
    public void createDirectoryTest(){
        try {
            boolean res = ossUtil.createDirectory("budin-oss1", "dxq");
            logger.info(Boolean.toString(res));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    //test ok
    @Test
    public void previewTest(){
        String url = ossUtil.preview("budin-oss1", "dxq");
        logger.info(url);
    }

}
