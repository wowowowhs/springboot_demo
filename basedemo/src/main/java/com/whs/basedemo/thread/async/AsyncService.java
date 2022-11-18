package com.whs.basedemo.thread.async;

import org.springframework.stereotype.Service;

@Service
public interface AsyncService {
    public Boolean execute(Integer num);

    public void sendEmail();

    public void  sendPush();

    /**
     * 测试同类方法，调用@Async标记的方法
     */
    public void testPush();
}
