package com.hongsheng;

import com.hongsheng.juc.thread.multiple.completefuturetest.ShopService;
import com.hongsheng.juc.thread.multiple.futuretest.FutureTaskDemo;
import com.hongsheng.juc.thread.multiple.futuretest.ThreadPoolConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

/**
 * 日常练习的单元测试
 */
@Slf4j
public class PracticeTest extends MyBaseTest{

    @Autowired
    private FutureTaskDemo futureTaskDemo;
    @Autowired
    private ShopService shopService;
    @Autowired
    private ThreadPoolConfig threadPoolConfig;

    @Test
    public void testThread(){
        futureTaskDemo.handleFuture();
    }

    @Test
    public void testShop() throws ExecutionException, InterruptedException {
        shopService.goods();
        shopService.syncGoods();
    }

    @Test
    public void test(){
        ThreadPoolTaskExecutor poolTaskExecutor = threadPoolConfig.getPoolTaskExecutor();
        System.out.println(poolTaskExecutor);
    }
}
