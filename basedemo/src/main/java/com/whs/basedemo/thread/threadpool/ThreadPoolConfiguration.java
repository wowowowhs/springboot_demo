package com.whs.basedemo.thread.threadpool;

import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Configuration
@Slf4j
public class ThreadPoolConfiguration {

    //https://www.cnblogs.com/vipsoft/p/16358156.html

    public static void main(String[] args) {

        //demo1
        /*ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,
                4,
                10L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(2),
                new ThreadFactory() {
                    private final AtomicInteger mThreadNum = new AtomicInteger(1);
                    @Override
                    public Thread newThread(Runnable r) {
                        Thread t = new Thread(r, "Thread-" + mThreadNum.getAndIncrement());
                        System.out.println(t.getName() + " has been created");
                        return t;
                    }
                },
                new ThreadPoolExecutor.RejectedExecutionHandler() {
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        // 可做日志记录等
                        System.err.println(r.toString() + " rejected " + executor.getTaskCount());
                    }
                });

        executor.prestartAllCoreThreads(); // 预启动所有核心线程
        for (int i = 1; i <= 10; i++) {
            final String taskName = String.valueOf(i);
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println(this.toString() + " is running!");
                        Thread.sleep(7000); //让任务执行慢点
                        System.out.println(Thread.currentThread().getName()+"执行结束");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public String toString() {
                    return "MyTask [name=" + taskName + "]";
                }
            });
        }*/

        //demo2
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2, 10, 10L,
                TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(2));
        executor.prestartAllCoreThreads(); // 预启动所有核心线程

        for (int i = 1; i <= 10; i++) {
            final String taskName = String.valueOf(i);
            MyRunnable myRunnable = new MyRunnable();
            executor.execute(myRunnable);
            System.out.println(String.format("工作中的线程数=%d,线程池中线程的数量=%d,队列中数量=%d",
                    executor.getActiveCount(), executor.getPoolSize(), executor.getQueue().size()));
            try {
                Thread.sleep(200);
            }catch (Exception e){
                System.out.println("exception....");
            }
        }
    }

    private static void printThreadPoolInfo(ThreadPoolExecutor threadPool){
        System.out.println(String.format("工作中的线程数=%d,线程池中线程的数量=%d,队列中数量=%d",
                threadPool.getActiveCount(), threadPool.getPoolSize(), threadPool.getQueue().size()));
    }


}

class MyRunnable implements Runnable{
    @Override
    public void run() {
        String name = Thread.currentThread().getName();
        System.out.println("name = "+name);
        try {
            Thread.sleep(9000);
        }catch (Exception e){
            System.out.println("exception....");
        }
        System.out.println(String.format("name=%s 执行结束", name));

    }
}
