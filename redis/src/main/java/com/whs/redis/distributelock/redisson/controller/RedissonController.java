package com.whs.redis.distributelock.redisson.controller;

import com.whs.redis.distributelock.redisson.service.RedissonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * redisson分布式锁实现
 * 参考：https://juejin.cn/post/7135307906031091749
 * 也可参考这篇：https://blog.csdn.net/qq_41910280/article/details/121769533
 * 还有这 ：https://juejin.cn/post/7023978870600630285
 */
@RestController
@RequestMapping("/redisson")
public class RedissonController {

    @Autowired
    private RedissonService redissonService;

    @RequestMapping("/redissonTest")
    public void  redissonTest(){
        redissonService.redissonTest();
    }
}
