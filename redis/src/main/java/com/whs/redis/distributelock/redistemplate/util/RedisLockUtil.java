package com.whs.redis.distributelock.redistemplate.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisLockUtil {

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 加锁
     *
     * @param key   redis主键
     * @param value 值
     */
    public boolean lock(String key, String value, long time) {
        final boolean result = Boolean.TRUE.equals(redisTemplate.opsForValue().setIfAbsent(key, value, time, TimeUnit.SECONDS));
        if (result) {
            log.info("[redisTemplate redis]设置锁缓存 缓存  url:{} ========缓存时间为{}秒", key, time);
        }
        return result;
    }

    /**
     * 解锁
     *
     * @param key redis主键
     */
    public boolean unlock(String key, String value) {
        if (Objects.equals(value, redisTemplate.opsForValue().get(key))) {
            final boolean result = Boolean.TRUE.equals(redisTemplate.delete(key));
            if (result) {
                log.info("[redisTemplate redis]释放锁 缓存  url:{}", key);
            }
            return result;
        }
        return false;
    }

    //从缓存中获取库存
    public int getStock(String key){
        ValueOperations<String, Integer> operations = redisTemplate.opsForValue();
        Integer stock = operations.get(key);
        if(stock==null){
            return 10;
        }
        return stock;
    }

    //设置库存
    public void setStock(String key, Integer stock){
        ValueOperations<String, Integer> operations = redisTemplate.opsForValue();
        operations.set(key, stock);
    }
}
