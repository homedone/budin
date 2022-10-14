package indiv.budin.fileservice;

import indiv.budin.entity.po.BudinFile;
import indiv.budin.mapper.BudinFileMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FileServiceApplication.class)
public class FileServiceMysqlTest {
    @Autowired
    private DataSource dataSource;
    public Logger logger = LoggerFactory.getLogger(FileServiceMysqlTest.class);
    @Autowired
    private BudinFileMapper budinFileMapper;

    @Test
    public void test() throws SQLException {
        Connection connection = dataSource.getConnection();
        logger.debug(connection.toString());
    }

    @Test
    public void mybatisTest() {
        BudinFile budinFile = new BudinFile(null, "filess", "ofiless", "txt", "/storage", "122");
        int res = budinFileMapper.insertSelective(budinFile);
        logger.debug(Integer.toString(res));
    }
}
