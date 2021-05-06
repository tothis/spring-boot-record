import com.example.CommonApplication;
import com.example.util.SpringUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

/**
 * @author 李磊
 */
@SpringBootTest(classes = CommonApplication.class)
class ApplicationTest {

    private Environment e;

    @Autowired
    public void setE(Environment e) {
        this.e = e;
    }

    @Test
    void contextLoads() {
        // test目录存在application.yml配置文件时 源目录application.yml失效
        System.out.println(SpringUtil.getProperty("name") + SpringUtil.getProperty("age"));
        System.out.println(e.getProperty("name") + e.getProperty("age"));
    }
}