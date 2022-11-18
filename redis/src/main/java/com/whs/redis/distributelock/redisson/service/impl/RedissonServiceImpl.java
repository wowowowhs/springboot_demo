package com.whs.redis.distributelock.redisson.service.impl;

import com.whs.redis.distributelock.redisson.service.RedissonService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedissonServiceImpl implements RedissonService {

    @Resource
    private RedissonClient redissonClient;

    /**
     * redisson锁测试
     *
     * @throws InterruptedException
     */
    @Override
    public void redissonTest() {
        // 获取锁
        RLock lock = redissonClient.getLock("lock");
        System.out.println("lock："+lock);
        try {
            // 获取锁 参数：获取锁的最大等待时间(期间会重试)，锁自动释放时间，时间单位
            boolean isLock = lock.tryLock(5, 15, TimeUnit.SECONDS);
            if (isLock) {
                System.out.println("获取成功");
                Thread.sleep(12000);
            } else {
                System.out.println("获取锁失败");
            }
        } catch (Exception e) {
            log.error("获取锁异常");
        } finally {
            // 释放锁
            try {
                lock.unlock();
                System.out.println("释放锁。。。。。。");
            }catch (Exception e){
                log.error("释放锁失败*******");
            }
        }
    }

}
