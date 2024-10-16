package com.yw.xxljobstudy.job;

import com.xxl.job.core.context.XxlJobContext;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author yuanwen
 * @description: 专业分类同步数据任务
 * @Since 2024-07-12 15:00
 **/
@Component
@Slf4j
public class CategoryTaskSyncSchedule {

    private static final ThreadPoolExecutor taskExecutor = new ThreadPoolExecutor(
            5,
            10,
            60,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(10));


    @XxlJob("taskTest") // 表示这是一个XXL-JOB的任务执行器中的定时任务，任务名称为"docReview"
    public void docReview() {
        XxlJobContext xxlJobContext = XxlJobContext.getXxlJobContext();
        for (int i = 0; i < 5; i++) {
            //线程池，需要传递当前任务上下文
            execTask(i,xxlJobContext);
        }
    }

    public void execTask(Integer n, XxlJobContext xxlJobContext) {
        CompletableFuture.runAsync(() -> {
            //设置线程池中执行线程当前任务的上下文
            XxlJobContext.setXxlJobContext(xxlJobContext);
            log.info("任务参数：{}",XxlJobHelper.getJobParam());
            XxlJobHelper.log("执行任务成功：{},任务参数：{}",n,XxlJobHelper.getJobParam());

        }, taskExecutor);
    }

}
