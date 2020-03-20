import com.example.UtilApplication;
import com.example.util.SpringUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

/**
 * @author 李磊
 * @datetime 2020/3/20 14:07
 * @description
 */
@SpringBootTest(classes = UtilApplication.class)
class UtilApplicationTests {

    @Autowired
    private Environment e;

    @Test
    void contextLoads() {
        // test目录存在application.yml配置文件时 源目录application.yml失效
        System.out.println(SpringUtil.getProperty("name") + SpringUtil.getProperty("age"));
        System.out.println(e.getProperty("name") + e.getProperty("age"));
    }
}