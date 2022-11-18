package com.whs.basedemo.thread.async;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Configuration
@EnableAsync
@Slf4j
public class SpringThreadPoolConfiguration {

    @Bean(name = "defaultThreadPoolExecutor", destroyMethod = "shutdown")
    public ThreadPoolExecutor systemCheckPoolExecutorService() {
        ////https://www.cnblogs.com/vipsoft/p/16358156.html
        return new ThreadPoolExecutor(3, 10, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(10000),
                new ThreadFactory() {
                    private final AtomicInteger mThreadNum = new AtomicInteger(1);
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r, "springThreadPool-Thread-" + mThreadNum.getAndIncrement());
                        System.out.println(t.getName() + " has been created");
                        return t;
                    }
                },
        (r, executor) -> log.error("system pool is full! "));
    }

    @Bean(name = "pushThreadPoolExecutor")
    public Executor pushThreadPoolExecutor() {
        ///https://blog.51cto.com/gblfy/5652672
        /*return new ThreadPoolExecutor(3, 10, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(10000),
                new ThreadFactory() {
                    private final AtomicInteger mThreadNum = new AtomicInteger(1);
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r, "pushThreadPool-Thread-" + mThreadNum.getAndIncrement());
                        System.out.println(t.getName() + " has been created");
                        return t;
                    }
                },
                (r, executor) -> log.error("system pool is full! "));*/
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //最大线程数
        executor.setMaxPoolSize(10);
        //核心线程数
        executor.setCorePoolSize(3);
        //任务队列的大小
        executor.setQueueCapacity(10);
        //线程池名的前缀
        executor.setThreadNamePrefix("pushThreadPool-");
        //允许线程的空闲时间30秒
        executor.setKeepAliveSeconds(30);
        //设置线程池关闭的时候等待所有任务都完成再继续销毁其他的Bean
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //设置线程池中任务的等待时间，如果超过这个时候还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住
        executor.setAwaitTerminationSeconds(10);

        /**
         * 拒绝处理策略
         * CallerRunsPolicy()：交由调用方线程运行，比如 main 线程。
         * AbortPolicy()：直接抛出异常。
         * DiscardPolicy()：直接丢弃。
         * DiscardOldestPolicy()：丢弃队列中最老的任务。
         */
        /**
         * 特殊说明：
         * 1. 这里演示环境，拒绝策略咱们采用抛出异常
         * 2.真实业务场景会把缓存队列的大小会设置大一些，
         * 如果，提交的任务数量超过最大线程数量或将任务环缓存到本地、redis、mysql中,保证消息不丢失
         * 3.如果项目比较大的话，异步通知种类很多的话，建议采用MQ做异步通知方案
         */
//        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        //线程初始化
        executor.initialize();
        return executor;
    }

}
