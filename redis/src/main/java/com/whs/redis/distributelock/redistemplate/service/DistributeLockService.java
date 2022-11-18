package com.whs.redis.distributelock.redistemplate.service;

import org.springframework.stereotype.Service;

@Service
public interface DistributeLockService {

    public void reduceStock();

}
