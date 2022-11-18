package com.whs.basedemo.thread.async;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AsyncServiceImpl implements AsyncService{

    @Async("defaultThreadPoolExecutor")
    @Override
    public Boolean execute(Integer num) {
        for(int i = 0;i<5;i++) {
            System.out.println("线程：" + Thread.currentThread().getName() + " , 任务：" + num);
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                System.out.println("exception....");
            }
        }
        System.out.println(Thread.currentThread().getName()+" 异步任务执行结束。。。。。。");
        return true;
    }

    @Async("defaultThreadPoolExecutor")
    @Override
    public void sendEmail() {
        for(int i =0;i<100;i++){
            UUID uuid = UUID.randomUUID();
            System.out.println(Thread.currentThread().getName() +" send email, email = "+uuid);
            try {
                Thread.sleep(500);
            }catch (Exception e){
                System.out.println("exception....");
            }
        }
    }

    @Override
    @Async("pushThreadPoolExecutor")
    public void sendPush() {
        for(int i =0;i<10;i++){
            UUID uuid = UUID.randomUUID();
            System.out.println(Thread.currentThread().getName() +" send push, lxj  pushId = "+uuid);
            try {
                Thread.sleep(2000);
            }catch (Exception e){
                System.out.println("exception....");
            }
        }
    }

    @Override
    public void testPush() {
        sendPush();
    }


}
