import com.example.entity.User;
import com.example.util.DroolsUtil;
import org.drools.core.base.RuleNameStartsWithAgendaFilter;
import org.drools.decisiontable.InputType;
import org.drools.decisiontable.SpreadsheetCompiler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.kie.api.KieServices;
import org.kie.api.io.Resource;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.internal.io.ResourceFactory;

import java.util.concurrent.TimeUnit;

class DroolsTest {

    private KieSession kieSession;

    @BeforeEach
    void before() {
        // date-effective和date-expires使用
        System.setProperty("drools.dateformat", "yyyy-MM-dd HH:mm:ss");
        kieSession = KieServices.Factory.get()
                .getKieClasspathContainer().newKieSession();
        kieSession.setGlobal("out", System.out);
    }

    @AfterEach
    void after() {
        kieSession.dispose();
    }

    @Test
    void testSalience() {
        kieSession.fireAllRules(new RuleNameStartsWithAgendaFilter("salience"));
    }

    /**
     * 每组只会执行成功一个 只要执行成功一个则立即停止
     */
    @Test
    public void testActivationGroup() {
        kieSession.fireAllRules(new RuleNameStartsWithAgendaFilter("activation_group"));
    }

    @Test
    public void testAgendaGroup() {
        // 获取焦点
        // kieSession.getAgenda().getAgendaGroup("agenda-group").setFocus();
        kieSession.fireAllRules(new RuleNameStartsWithAgendaFilter("agenda_group"));
    }

    @Test
    public void testTimer() throws InterruptedException {
        new Thread(() -> kieSession.fireUntilHalt(new RuleNameStartsWithAgendaFilter("timer"))).start();

        // junit运行需睡眠
        TimeUnit.SECONDS.sleep(10);

        // 结束规则引擎
        kieSession.halt();
    }

    @Test
    public void testDateEffective() {
        kieSession.fireAllRules(new RuleNameStartsWithAgendaFilter("date_effective"));
    }

    @Test
    public void testDateExpires() {
        kieSession.fireAllRules(new RuleNameStartsWithAgendaFilter("date_expires"));
    }

    @Test
    public void testQuery() {
        User user = new User();
        user.setAge(4);
        user.setName("lilei");
        kieSession.insert(user);

        QueryResults rows = kieSession.getQueryResults("query_user_age");
        System.out.println("符合条件的fact对象数量 " + rows.size());
        rows.forEach(row -> System.out.println(row.get("$user")));

        rows = kieSession.getQueryResults("query_user_name", "lilei");
        System.out.println("符合条件的fact对象数量 " + rows.size());
        rows.forEach(row -> System.out.println(row.get("$user")));
    }

    @Test
    public void testDecision() {
        SpreadsheetCompiler compiler = new SpreadsheetCompiler();
        Resource resource = ResourceFactory.newClassPathResource("decision.xlsx");
        String drlContent = compiler.compile(resource, InputType.XLS);
        System.out.println(drlContent);

        KieSession kieSession = DroolsUtil.createKieSessionByDrlContent(drlContent);

        User user = new User();
        user.setName("frank");
        user.setAge(19);

        // 将user作为参数传入drools执行
        kieSession.insert(user);
        // 加载规则 不设置过滤器则触发所有规则
        System.out.println(kieSession.fireAllRules());
        System.out.println(user.getName());

        // 关闭会话
        kieSession.dispose();
    }
}