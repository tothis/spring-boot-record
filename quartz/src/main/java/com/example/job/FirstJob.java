package com.example.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
// 保证相同JobDetail在多个JVM进程中 有且仅有一个节点在执行
@DisallowConcurrentExecution
public class FirstJob extends QuartzJobBean {
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        log.info("first执行");
    }
}