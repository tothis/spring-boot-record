import com.example.BaseTestApplication;
import com.example.util.ContextUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

/**
 * @author 李磊
 */
@SpringBootTest(classes = BaseTestApplication.class)
class BaseTestApplicationTests {

    @Autowired
    private Environment e;

    @Test
    void contextLoads() {
        // test 目录存在 application.yml 配置文件时，源目录 application.yml 失效
        System.out.println(ContextUtil.getProperty("name") + ContextUtil.getProperty("age"));
        System.out.println(e.getProperty("name") + e.getProperty("age"));
    }
}
