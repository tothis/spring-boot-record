package com.example.config;

import com.example.job.FirstJob;
import com.example.job.SecondJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 李磊
 * @datetime 2020/3/17 10:52
 * @description
 */
@Configuration
public class QuartzConfig {

//    public static class Job1Config {
//
//        @Bean
//        public JobDetail job1() {
//            return JobBuilder.newJob(FirstJob.class)
//                    .withIdentity("job1") // 名字为 job1
//                    // 没有trigger关联时 任务是否被保留 因为创建JobDetail时 还没Trigger指向它 所以需要设置为true 表示保留
//                    .storeDurably()
//                    .build();
//        }
//
//        @Bean
//        public Trigger job1Trigger() {
//            // 简单的调度计划的构造器
//            SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
//                    .withIntervalInSeconds(5) // 频率
//                    .repeatForever(); // 次数
//            // trigger构造器
//            return TriggerBuilder.newTrigger()
//                    .forJob(job1()) // 对应 Job为job1
//                    .withIdentity("job1Trigger") // 名字为job1Trigger
//                    .withSchedule(scheduleBuilder) // 对应Schedule为scheduleBuilder
//                    .build();
//        }
//    }
//
//    public static class Job2Config {
//        @Bean
//        public JobDetail job2() {
//            return JobBuilder.newJob(SecondJob.class).withIdentity("job2").storeDurably().build();
//        }
//        @Bean
//        public Trigger job2Trigger() {
//            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ? *");
//            return TriggerBuilder.newTrigger().forJob(job2()).withIdentity("job2Trigger").withSchedule(scheduleBuilder).build();
//        }
//    }
}