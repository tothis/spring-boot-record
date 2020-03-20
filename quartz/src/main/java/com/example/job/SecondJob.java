package com.example.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@DisallowConcurrentExecution
public class SecondJob extends QuartzJobBean {

    private AtomicInteger count = new AtomicInteger();

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) {
        log.info("second第{}次执行", count.incrementAndGet());
    }
}