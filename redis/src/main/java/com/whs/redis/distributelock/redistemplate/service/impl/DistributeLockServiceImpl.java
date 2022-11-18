package com.whs.redis.distributelock.redistemplate.service.impl;

import com.whs.redis.distributelock.redistemplate.service.DistributeLockService;
import com.whs.redis.distributelock.redistemplate.util.RedisLockUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DistributeLockServiceImpl implements DistributeLockService {

    @Autowired
    private RedisLockUtil redisLockUtil;

    @Override
    public void reduceStock() {
        boolean lock;
        int stock;
        String uuid = UUID.randomUUID().toString();

        try {
            lock = redisLockUtil.lock("stocklock", uuid, 10l);
            if (!lock) {
                //重试获取锁
                while (true){
                    lock = redisLockUtil.lock("stocklock", uuid, 10l);
                    if(lock){
                        break;
                    }
                    System.out.println("服务繁忙,稍后再试...");
                    Thread.sleep(500);
                }
            }
            stock = redisLockUtil.getStock("stock");
            System.out.println(String.format("当前线程：%s,当前库存：%d", Thread.currentThread().getName(), stock));
            if (stock > 0) {
                redisLockUtil.setStock("stock", --stock);
            }else {
                System.out.println("库存已经为0.......");
            }
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 在此释放锁时,判断锁是为自己持有才进行释放
            redisLockUtil.unlock("stocklock", uuid);
        }
    }
}
