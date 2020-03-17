import com.example.QuartzApplication;
import com.example.job.FirstJob;
import com.example.job.SecondJob;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.collections.Sets;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author 李磊
 * @datetime 2020/3/17 12:34
 * @description
 */
@SpringBootTest(classes = QuartzApplication.class)
public class QuartzApplicationTests {

    @Autowired
    private Scheduler scheduler;

    @Test
    public void addJob1() throws SchedulerException {
        // 创建JobDetail
        JobDetail jobDetail = JobBuilder.newJob(FirstJob.class).withIdentity("job1").storeDurably().build();
        // 创建Trigger
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(5) // 频率
                .repeatForever(); // 次数
        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail).withIdentity("job1Trigger")
                .withSchedule(scheduleBuilder).build();
        // 添加调度任务
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.scheduleJob(jobDetail, Sets.newSet(trigger), true);
    }

    @Test
    public void addJob2() throws SchedulerException {
        // 创建JobDetail
        JobDetail jobDetail = JobBuilder.newJob(SecondJob.class).withIdentity("job2").storeDurably().build();
        // 创建Trigger
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ? *");
        Trigger trigger = TriggerBuilder.newTrigger().forJob(jobDetail).withIdentity("job2Trigger")
                .withSchedule(scheduleBuilder).build();
        // 添加调度任务
        scheduler.scheduleJob(jobDetail, trigger);
        scheduler.scheduleJob(jobDetail, Sets.newSet(trigger), true);
    }
}