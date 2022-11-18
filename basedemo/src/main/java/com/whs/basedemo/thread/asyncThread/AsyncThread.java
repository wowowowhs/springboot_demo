package com.whs.basedemo.thread.asyncThread;

public class AsyncThread extends Thread{

    public void run(){
//        System.out.println("当前线程名称:" + this.getName() + ", 执行线程名称:" + Thread.currentThread().getName() + "-hello");
        for (int i = 0; i < 10; i++) {
            System.out.println("当前线程名称："+this.getName()+"  "+i);
            try {
                Thread.sleep(500);
            }catch (Exception e){
                System.out.println("主线程异常");
            }
        }
        System.out.println(this.getName()+"线程执行结束");
    }
}
