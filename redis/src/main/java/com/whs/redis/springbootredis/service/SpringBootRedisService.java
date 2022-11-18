package com.whs.redis.springbootredis.service;

import org.springframework.stereotype.Service;

@Service
public interface SpringBootRedisService {

    public void getInfoByCache(Integer id);

    public void getDtoWithCache();

}
