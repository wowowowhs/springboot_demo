package com.whs.redis.springbootredis.service.impl;

import com.whs.redis.springbootredis.entity.User;
import com.whs.redis.springbootredis.service.SpringBootRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class SpringBootRedisServiceImpl implements SpringBootRedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void getInfoByCache(Integer id) {
        String keyPreFix = "redis:test:";
        String key = keyPreFix+id;
        String value = UUID.randomUUID().toString();
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        boolean hasKey = redisTemplate.hasKey(key);
        if(hasKey){
            String valueOfCache = operations.get(key);
            System.out.println("从缓存中获取，value :" +valueOfCache);
        }else{
            System.out.println(String.format("缓存中没有值，key : %s, value : %s", key, value));
            operations.set(key, value, 30, TimeUnit.SECONDS);   //30秒失效
        }
    }

    @Override
    public void getDtoWithCache() {
        String key = "redis:test:user";
        User user = new User(1, "zhangsan",26);
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        boolean hasKey = redisTemplate.hasKey(key);
        if (hasKey) {
            User userOfCache = operations.get(key);
            System.out.println("*************从缓存中获得数据 name:"+userOfCache.getName());
        } else {
            System.out.println("===========缓存中没有数据===========");
            operations.set(key, user, 30, TimeUnit.SECONDS);   //30秒失效
        }
    }
}
