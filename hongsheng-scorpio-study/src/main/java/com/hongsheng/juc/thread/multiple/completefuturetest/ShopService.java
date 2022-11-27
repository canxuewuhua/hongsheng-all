package com.hongsheng.juc.thread.multiple.completefuturetest;

import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * completableFuture和FutureTask都是Future的实现类
 */
@Service
public class ShopService {

    public void goods(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        System.out.println(userInfo());
        System.out.println(goodsInfo());
        System.out.println(commentInfo());

        stopWatch.stop();
        System.out.println("[goods] execute time=" + stopWatch.getTotalTimeMillis() + "ms");
    }

    public void syncGoods() throws ExecutionException, InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // async supplyAsync用于有返回值的任务 runAsync则用于没有返回值的任务
        // 主线程结束 守护线程不结束
        CompletableFuture<String> s1 = CompletableFuture.supplyAsync(this::userInfo);
        CompletableFuture<String> s2 = CompletableFuture.supplyAsync(this::goodsInfo);
        CompletableFuture<String> s3 = CompletableFuture.supplyAsync(this::commentInfo);

        CompletableFuture<String> stringCompletableFuture = s1.thenCombine(s2, (o1, o2) -> o1 + ":" + o2).thenCombine(s3, (o1, o2) -> o1 + ":" + o2);
        String ret = stringCompletableFuture.get();
        System.out.println(ret);


        stopWatch.stop();
        System.out.println("[syncGoods] execute time=" + stopWatch.getTotalTimeMillis() + "ms");
    }

    public String userInfo(){
        try{
            Thread.sleep(200L);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "userInfo";
    }

    public String goodsInfo(){
        try{
            Thread.sleep(200L);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "goodsInfo";
    }

    public String commentInfo(){
        try{
            Thread.sleep(200L);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "commentInfo";
    }
}
