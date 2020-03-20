package com.example.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
// 保证相同JobDetail在多个JVM进程中 有且仅有一个节点在执行
@DisallowConcurrentExecution
public class FirstJob extends QuartzJobBean {

    private AtomicInteger count = new AtomicInteger();

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        log.info("first第{}次执行", count.incrementAndGet());
    }
}