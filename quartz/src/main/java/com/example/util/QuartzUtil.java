package com.example.util;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class QuartzUtil {

    private static Scheduler scheduler;

    @Autowired
    public void setScheduler(Scheduler scheduler) {
        QuartzUtil.scheduler = scheduler;
    }

    private static String JOB_GROUP_NAME = "ATAO_JOBGROUP"; // 任务组
    private static String TRIGGER_GROUP_NAME = "ATAO_TRIGGERGROUP"; // 触发器组

    /**
     * 添加一个定时任务 使用默认的任务组名 触发器名 触发器组名
     *
     * @param jobName  任务名
     * @param jobClass 任务
     * @param time     时间设置 参考quartz说明文档
     */
    public static void add(String jobName, Class<? extends Job> jobClass, String time) {
        // 用于描叙Job实现类及其他的一些静态信息 构建一个作业实例
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, JOB_GROUP_NAME).build();
        CronTrigger trigger = TriggerBuilder
                .newTrigger() // 创建一个新的TriggerBuilder来规范一个触发器
                .withIdentity(jobName, TRIGGER_GROUP_NAME) // 给触发器起一个名字和组名
                .withSchedule(CronScheduleBuilder.cronSchedule(time))
                .build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            if (!scheduler.isShutdown()) {
                scheduler.start(); // 启动
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加一个定时任务 使用默认的任务组名 触发器名 触发器组名 带参数
     *
     * @param jobName  任务名
     * @param jobClass 任务
     * @param time     时间设置 参考quartz说明文档
     */
    public static void add(String jobName, Class<? extends Job> jobClass, String time, Map<String, Object> parameter) {
        // 用于描叙Job实现类及其他的一些静态信息 构建一个作业实例
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, JOB_GROUP_NAME).build();
        jobDetail.getJobDataMap().put("parameterList", parameter); // 传参数
        CronTrigger trigger = TriggerBuilder
                .newTrigger() // 创建一个新的TriggerBuilder来规范一个触发器
                .withIdentity(jobName, TRIGGER_GROUP_NAME) // 给触发器起一个名字和组名
                .withSchedule(CronScheduleBuilder.cronSchedule(time))
                .build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            if (!scheduler.isShutdown()) {
                scheduler.start(); // 启动
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加一个定时任务
     *
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param jobClass         任务
     * @param time             时间设置 参考quartz说明文档
     */
    public static void add(String jobName, String jobGroupName,
                           String triggerName, String triggerGroupName, Class<? extends Job> jobClass,
                           String time) {
        // 任务名 任务组 任务执行类
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
        CronTrigger trigger = TriggerBuilder // 触发器
                .newTrigger()
                .withIdentity(triggerName, triggerGroupName)
                .withSchedule(CronScheduleBuilder.cronSchedule(time))
                .build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            if (!scheduler.isShutdown()) {
                scheduler.start(); // 启动
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加一个定时任务 带参数
     *
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     * @param jobClass         任务
     * @param time             时间设置 参考quartz说明文档
     */
    public static void add(String jobName, String jobGroupName, String triggerName
            , String triggerGroupName, Class<? extends Job> jobClass
            , String time, Map<String, Object> parameter) {
        // 任务名 任务组 任务执行类
        JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(jobName, jobGroupName).build();
        jobDetail.getJobDataMap().put("parameterList", parameter); // 传参数
        CronTrigger trigger = TriggerBuilder // 触发器
                .newTrigger()
                .withIdentity(triggerName, triggerGroupName)
                .withSchedule(CronScheduleBuilder.cronSchedule(time))
                .build();
        try {
            scheduler.scheduleJob(jobDetail, trigger);
            if (!scheduler.isShutdown()) {
                scheduler.start(); // 启动
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改一个任务的触发时间 使用默认的任务组名 触发器名 触发器组名
     *
     * @param jobName 任务名
     * @param time    新的时间设置
     */
    public static void modifyTime(String jobName, String time) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME); // 通过触发器名和组名获取TriggerKey
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey); // 通过TriggerKey获取CronTrigger
            if (trigger == null) {
                return;
            }
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(time)) {
                JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME); // 通过任务名和组名获取JobKey
                JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                Class<? extends Job> jobClass = jobDetail.getJobClass();
                QuartzUtil.remove(jobName);
                QuartzUtil.add(jobName, jobClass, time);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 修改一个任务的触发时间
     *
     * @param triggerName      任务名称
     * @param triggerGroupName 传过来的任务名称
     * @param time             更新后的时间规则
     */
    public static void modifyTime(String triggerName, String triggerGroupName, String time) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName); // 通过触发器名和组名获取TriggerKey
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey); // 通过TriggerKey获取CronTrigger
            if (trigger == null) return;
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(trigger.getCronExpression());
            String oldTime = trigger.getCronExpression();
            if (!oldTime.equalsIgnoreCase(time)) {
                trigger = trigger.getTriggerBuilder() // 重新构建trigger
                        .withIdentity(triggerKey)
                        .withSchedule(scheduleBuilder)
                        .withSchedule(CronScheduleBuilder.cronSchedule(time))
                        .build();
                scheduler.rescheduleJob(triggerKey, trigger); // 按新的trigger重新设置job执行
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 移除一个任务 使用默认的任务组名 触发器名 触发器组名
     *
     * @param jobName 任务名称
     */
    public static void remove(String jobName) {
        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, TRIGGER_GROUP_NAME); // 通过触发器名和组名获取TriggerKey
        JobKey jobKey = JobKey.jobKey(jobName, JOB_GROUP_NAME); // 通过任务名和组名获取JobKey
        try {
            scheduler.pauseTrigger(triggerKey); // 停止触发器
            scheduler.unscheduleJob(triggerKey); // 移除触发器
            scheduler.deleteJob(jobKey); // 删除任务
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 移除一个任务
     *
     * @param jobName          任务名
     * @param jobGroupName     任务组名
     * @param triggerName      触发器名
     * @param triggerGroupName 触发器组名
     */
    public static void remove(String jobName, String jobGroupName, String triggerName, String triggerGroupName) {
        TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName); // 通过触发器名和组名获取TriggerKey
        JobKey jobKey = JobKey.jobKey(jobName, jobGroupName); // 通过任务名和组名获取JobKey
        try {
            scheduler.pauseTrigger(triggerKey); // 停止触发器
            scheduler.unscheduleJob(triggerKey); // 移除触发器
            scheduler.deleteJob(jobKey); // 删除任务
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 启动所有定时任务
     */
    public static void start() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭所有定时任务 无法重启
     */
    public static void shutdown() {
        try {
            if (!scheduler.isShutdown())
                scheduler.shutdown();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 暂停所有定时任务
     */
    public static void standby() {
        try {
            scheduler.standby();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}