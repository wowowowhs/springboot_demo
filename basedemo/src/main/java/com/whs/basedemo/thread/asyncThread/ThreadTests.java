package com.whs.basedemo.thread.asyncThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTests {

    //https://blog.csdn.net/ChenRui_yz/article/details/126926950
    public static void main(String[] args) {
        //1.异步线程调用
        // 创建异步线程
        /*AsyncThread asyncThread = new AsyncThread();
        // 启动异步线程
        asyncThread.start();
        for (int i = 0; i < 10; i++) {
            System.out.println("主线程打印："+i);
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                System.out.println("主线程异常");
            }
        }*/

        //2.线程池
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(new Runnable(){
            public void run() {
                try {
                    //要执行的业务代码，我们这里没有写方法，可以让线程休息几秒进行测试
                    Thread.sleep(10000);
                    System.out.print("睡够啦~");
                    System.out.println(1/0);
                }catch(Exception e) {
                    throw new RuntimeException("报错啦！！");
                }
            }
        });
        for (int i = 0; i < 5; i++) {
            System.out.println("主线程打印：" + i);
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                System.out.println("主线程异常");
            }
        }
    }

}
