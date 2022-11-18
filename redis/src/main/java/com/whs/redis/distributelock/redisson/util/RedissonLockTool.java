package com.whs.redis.distributelock.redisson.util;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 *
 * 参考：https://blog.csdn.net/qq_41910280/article/details/121769533
 *
 * Redisson分布式锁
 * lockName是hash-hash值
 * RedissonClient会有一个uuid标识, 再加线程id, 组成hash-key值(即 非同一个线程无法解锁)
 * hash-value值为重入次数
 *
 * @author zhouyou
 * @date 2020/1/17 17:22
 * @email zhouyouchn@126.com
 */
@Component
public class RedissonLockTool {

    private static final Logger logger = LoggerFactory.getLogger(RedissonLockTool.class);
    @Autowired
    RedissonClient redisson;
    private final TimeUnit unit = TimeUnit.MILLISECONDS;

    /**
     * 加锁 watchdog自动续期
     * 如果没有在finally释放 可能会导致死锁
     *
     * @param lockName
     */
    public void lock(String lockName) {
        RLock rLock = redisson.getLock(lockName);
        rLock.lock();
    }

    /**
     * 加锁
     *
     * @param lockName
     * @param leaseTime 锁最大有效时间
     */
    public void lock(String lockName, long leaseTime) {
        RLock rLock = redisson.getLock(lockName);
        rLock.lock(leaseTime, unit);
    }

    /**
     * 加锁 成功之前最多等待waitTime时间
     *
     * @param lockName
     * @param waitTime
     * @return
     */
    public boolean tryLock(String lockName, long waitTime) {
        RLock rLock = redisson.getLock(lockName);
        boolean getLock = false;
        try {
            getLock = rLock.tryLock(waitTime, unit);
        } catch (InterruptedException e) {
            logger.error("tryLock fail，lockName=" + lockName, e);
        }
        return getLock;
    }

    /**
     * 加锁操作
     *
     * @param lockName
     * @param leaseTime 锁最大有效时间
     * @param waitTime  加锁等待时间
     * @return
     */
    public boolean tryLock(String lockName, long leaseTime, long waitTime) {
        RLock rLock = redisson.getLock(lockName);
        boolean getLock = false;
        try {
            getLock = rLock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            logger.error("tryLock fail，lockName=" + lockName, e);
        }
        return getLock;
    }

    /**
     * 解锁
     *
     * @param lockName
     */
    public boolean unlock(String lockName) {
        try {
            redisson.getLock(lockName).unlock();
        } catch (Exception e) {
            logger.error("unlock fail", e);
            return false;
        }
        return true;
    }

    /**
     * 该锁是否已经被任何线程锁定
     *
     * @param lockName 锁名称
     */
    public boolean isLocked(String lockName) {
        RLock rLock = redisson.getLock(lockName);
        return rLock.isLocked();
    }


    /**
     * 该锁是否已经被当前线程锁定
     *
     * @param lockName 锁名称
     */
    public boolean holdsLock(String lockName) {
        RLock rLock = redisson.getLock(lockName);
        return rLock.isHeldByCurrentThread();
    }

}
