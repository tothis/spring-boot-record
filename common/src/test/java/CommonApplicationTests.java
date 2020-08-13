import com.example.CommonApplication;
import com.example.component.ThisTemplate;
import com.example.util.FileUtil;
import com.example.util.SpringUtil;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;

/**
 * @author 李磊
 * @datetime 2020/3/20 14:07
 * @description
 */
@SpringBootTest(classes = CommonApplication.class)
class CommonApplicationTests {

    @Autowired
    private Environment e;

    @Autowired
    private ThisTemplate template;

    @Test
    void contextLoads() {
        // test目录存在application.yml配置文件时 源目录application.yml失效
        System.out.println(SpringUtil.getProperty("name") + SpringUtil.getProperty("age"));
        System.out.println(e.getProperty("name") + e.getProperty("age"));
    }

    @Test
    void testStarter() {
        System.out.println(template.one());
        System.out.println(template.two());
    }

    @SneakyThrows
    @Test
    void testPath() {
        // junit测试和源码中获取相对目录不一致 junit会包含模块名称 源码不会包含模块名称
        FileUtil.testPath("pom.xml");

        File file1 = ResourceUtils.getFile("classpath:test.json");
        byte[] result1 = new byte[1];
        FileInputStream in1 = new FileInputStream(file1);
        in1.read(result1);
        in1.close();
        System.out.println("成功 -> " + new String(result1));

        File file2 = ResourceUtils.getFile("classpath:application.yml");
        byte[] result2 = new byte[1];
        FileInputStream in2 = new FileInputStream(file2);
        in2.read(result2);
        in2.close();
        System.out.println("成功 -> " + new String(result2));
    }
}