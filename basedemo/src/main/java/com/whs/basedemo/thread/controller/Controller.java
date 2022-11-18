package com.whs.basedemo.thread.controller;

import com.whs.basedemo.thread.async.AsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @Autowired
    private AsyncService asyncService;

    @RequestMapping("/test")
    public void test(){
        System.out.println("test...............");
    }

    @RequestMapping("/testasync")
    public void AsyncTest(Integer num){
        for(int i = 0;i < 3; i++) {
            asyncService.execute(num);
        }
        System.out.println("ending......");
    }

    @RequestMapping("/sendemail")
    public String AsyncTestSendEmail(){
        asyncService.sendEmail();
        return "send email ending......";
    }

    @RequestMapping("/sendpush")
    public String AsyncTestSendPush(){
        asyncService.sendPush();
        return "send push ending......lxj";
    }

    @RequestMapping("/testPush")
    public String testPush(){
        asyncService.testPush();
        return "testPush ending......";
    }

}
