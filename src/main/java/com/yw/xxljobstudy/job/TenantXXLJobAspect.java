//package com.yw.xxljobstudy.job;
//
//import com.xxl.job.core.context.XxlJobContext;
//import com.xxl.job.core.handler.annotation.XxlJob;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.stereotype.Component;
//
//import java.util.Map;
//import java.util.concurrent.ArrayBlockingQueue;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
//@Aspect
//@Slf4j
//@Component
//public class TenantXXLJobAspect {
//
//    private static final ThreadPoolExecutor taskExecutor = new ThreadPoolExecutor(
//            5,
//            10,
//            60,
//            TimeUnit.SECONDS,
//            new ArrayBlockingQueue<>(10));
//
//    @Around("@annotation(xxlJob)")
//    public Object around(ProceedingJoinPoint joinPoint, XxlJob xxlJob) throws Throwable {
//        execute(joinPoint, xxlJob);
//        return null; // JobHandler 无返回
//    }
//
//    private void execute(ProceedingJoinPoint joinPoint, XxlJob xxlJob) {
//
//        // 逐个租户，执行 Job
//        Map<String, String> results = new ConcurrentHashMap<>();
//        XxlJobContext xxlJobContext = XxlJobContext.getXxlJobContext();
//        for (int i = 0; i < 5; i++) {
//            int finalI = i;
//            executeXxljob(() -> {
//                try {
//                    XxlJobContext.setXxlJobContext(xxlJobContext);
//                    joinPoint.proceed();
//                } catch (Throwable e) {
//                    results.put(String.valueOf(finalI), e.toString());
//                }
//            }, taskExecutor);
//        }
//    }
//
//    public static void executeXxljob(Runnable runnable, ThreadPoolExecutor threadPoolExecutor) {
//        // 执行逻辑
//        threadPoolExecutor.submit(runnable);
//    }
//
//}
