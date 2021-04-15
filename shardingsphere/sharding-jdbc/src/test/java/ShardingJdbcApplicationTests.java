import com.example.ShardingJdbcApplication;
import com.example.mapper.UserMapper;
import com.example.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 李磊
 * @since 1.0
 */
@SpringBootTest(classes = ShardingJdbcApplication.class)
public class ShardingJdbcApplicationTests {

    @Autowired
    private UserMapper mapper;

    @Test
    void create() {
        StringBuilder sql = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sql.append(String.format("create table if not exists `user%d` (" +
                    "`id` bigint not null auto_increment," +
                    "`name` char(10) default '' comment '名称'," +
                    "primary key (`id`)" +
                    ");", i));
        }
        System.out.println("sql : " + sql);
        mapper.exec(sql.toString());
    }

    @Test
    void insert() {
        for (int i = 0; i < 50; i++) {
            mapper.insert(new User((long) i, "name-" + i));
        }
    }
}