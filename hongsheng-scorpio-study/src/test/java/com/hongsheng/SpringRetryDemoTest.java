package com.hongsheng;

import com.hongsheng.retry.spring.SpringRetryDemo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SpringRetryDemoTest extends MyBaseTest{

    @Autowired
    private SpringRetryDemo springRetryDemo;

    @Test
    public void retry(){
        boolean abc = springRetryDemo.call("abc");
        log.info("--结果是:{}--",abc);
    }
}
