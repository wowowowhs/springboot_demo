package com.whs.redis.springbootredis.controller;

import com.whs.redis.springbootredis.service.SpringBootRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisTestController {

    @Autowired
    private SpringBootRedisService springBootRedisService;

    @RequestMapping("/getInfoByCache")
    public void getInfoFromCache(Integer id){
        springBootRedisService.getInfoByCache(id);
    }

    @RequestMapping("/getDtoWithCache")
    public void getDtoWithCache(){
        springBootRedisService.getDtoWithCache();
    }

}
