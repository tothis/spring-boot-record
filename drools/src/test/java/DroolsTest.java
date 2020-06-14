import com.example.entity.User;
import org.junit.jupiter.api.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieSession;

class DroolsTest {
    @Test
    void contextLoads() {
        KieSession kieSession = KieServices.Factory.get().getKieClasspathContainer()
                .newKieSession("ksession-name");

        User user1 = new User();
        user1.setAge((byte) 0);
        User user2 = new User();
        user2.setAge((byte) 18);

        kieSession.insert(user1); // 将user作为参数传入drools执行
        System.out.println(kieSession.fireAllRules()); // 加载规则
        System.out.println(user1.getUserName());

        kieSession.insert(user2);
        System.out.println(kieSession.fireAllRules());
        System.out.println(user2.getUserName());
    }
}