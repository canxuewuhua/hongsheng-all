package com.hongsheng.juc.thread.multiple.completefuturetest;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.hongsheng.juc.thread.multiple.futuretest.ThreadPoolConfig;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.time.Instant;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 参考
 * https://blog.csdn.net/zzzgd_666/article/details/83620984
 */
@Slf4j
public class CompleteFutureTest {

    ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("thread-%d").build();
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(30,30,0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(1024), threadFactory);

    private Instant startTime;
    private Instant endTime;

    @Autowired
    private ThreadPoolConfig threadPoolConfig;
    /**
     * 线程测试方法
     *
     * @return
     */
    public static Integer run(int a, int b,int sec) {
        try {
            log.info("----------run方法开始执行,休眠{}秒-------\ta:{}\tb:{}",sec, a, b);
            Thread.sleep(sec * 1000);
            int i = a / b;
            log.info("----------run方法结束执行--------\tresult:{}", i);
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 线程测试方法
     *
     * @return
     */
    public static Integer run2(int a) {
        //当a为偶数的时候,抛出一个异常
        if (a % 2 == 0) {
            int i = 1 / 0;
        }
        int i = a / 2;
        return i;
    }

    /**
     * 线程测试方法
     *
     * @return
     */
    public static String run3(String s, int a) {
        try {
            log.info(Thread.currentThread().getName()+"----------开始休眠{}秒-------", a);
            Thread.sleep(a*1000);
            log.info(Thread.currentThread().getName()+"----------休眠结束--------");
            return s;
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }



    public void sleep(long sec) throws InterruptedException {
        log.info("主线程休眠{}秒....", sec);
        TimeUnit.SECONDS.sleep(sec);
        log.info("主线程休眠结束....");
    }


    @Before
    public void before() {
        log.info("开始测试");
        startTime = Instant.now();
    }
    @After
    public void after() {
        endTime = Instant.now();
        log.info("结束测试,共耗时:\t{}", ChronoUnit.MILLIS.between(startTime, endTime));

    }

    /**
     * 创建一个简单的没有返回值的CompleteFuture
     * runAsync 没有返回值
     * 耗时 3038
     */
    @Test
    public void fun01() throws InterruptedException {
//        ThreadPoolTaskExecutor poolTaskExecutor = threadPoolConfig.getPoolTaskExecutor();
        CompletableFuture.runAsync(() -> run(10, 5,1), threadPoolExecutor);
        sleep(3);
    }

    /**
     * 创建一个简单的没有返回值的CompleteFuture
     * supplyAsync 有返回值,类似Future
     * CompletableFuture使用supplyAsync(),会异步调用方法,调用get()方法,会在获取到future返回值后,再执行get()以后的程序
     * 耗时 3040
     */
    @Test
    public void fun02() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> f = CompletableFuture.supplyAsync(() -> run(10, 5,1), threadPoolExecutor);

        log.info("----get阻塞开始----");
        Integer i = f.get();
        log.info("----get阻塞结束----");

        log.info("获取的值是:{}",i);
    }

    /**
     * thenApply()  监听future返回,调用Future方法对返回值进行修改和操作,这个操作有返回值,比如转换类型
     * thenAccept() 监听future返回,调用Consumer处理返回值,处理的结果没有返回值,比如打印结果,这样的话最终的CompletableFuture也拿不到返回值
     * thenRun()    监听future返回,然后自己自定义处理
     * 耗时 7037 ,线程都是同一个demo-pool-0
     */
    @Test
    public void fun03() throws InterruptedException, ExecutionException {
        CompletableFuture<Void> f = CompletableFuture.supplyAsync(() -> run(10, 5, 1), threadPoolExecutor)
                .thenApply(i ->String.valueOf(i).concat("abc"))
                .thenAccept(s ->log.info("thenAccept接收的结果是:{}", s))
                .thenRun(() ->log.info("在业务后处理其他的流程"))
                .thenAccept(s -> log.info("全部结束"));
        f.get();
    }

    /**
     * thenApplyAsync()  异步 调用Future方法对返回值进行修改和操作,这个操作有返回值,比如转换类型
     * thenAcceptAsync() 异步 调用Consumer处理返回值,处理的结果没有返回值,比如打印结果  最终CompletableFuture也拿不到返回值
     * thenRunAsync()    异步
     * <p>
     * 与thenApply,thenAccept,thenRun相比,是另起一个线程执行,但是因为他们都需要拿到上一个方法的值,所以这里异步开启线程与同步耗时是一样的
     * 耗时:7045  线程不一样,都开启了新线程
     */
    @Test
    public void fun04() throws Exception {
        CompletableFuture<Void> f = CompletableFuture.supplyAsync(() -> run(10, 5, 1), threadPoolExecutor)
                .thenApplyAsync(i -> {
                    log.info("thenApplyAsync休眠3秒");
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return String.valueOf(i).concat("abc");
                }, threadPoolExecutor)
                .thenAcceptAsync(s -> {
                    log.info("thenAcceptAsync接收的结果是:{},休眠2秒", s);
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }, threadPoolExecutor)
                .thenRunAsync(() -> {
                    log.info("thenRunAsync开一个线程,休眠1秒");
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }, threadPoolExecutor)
                .thenAccept(s -> log.info("全部结束"));

        f.get();

    }

    /**
     * 处理异常 ,传入1到10的数调用run2(),当传入的是偶数会抛出异常
     * exceptionally 对抛出的异常进行处理
     */
    @Test
    public void fun05() {
        //流式处理,从1到10异步调用run2(),将future都存到list中
        List<CompletableFuture<Integer>> futures = IntStream.range(1, 11)
                .mapToObj(i -> CompletableFuture.supplyAsync(() -> run2(i), threadPoolExecutor).exceptionally(e -> {
                    log.info("抛出了一个异常:{}", e.getCause().toString());
                    //返回一个默认值
                    return 0;
                }))
                .collect(Collectors.toList());
        //获取值
        String res = futures.stream()
                .map(f -> {
                    try {
                        return f.thenApply(String::valueOf).get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return "";
                }).collect(Collectors.joining(" , ", "", ""));
        System.out.println("res = " + res);
    }

    /**
     * 多层结构的future返回一个结果,对应的是java8的flatmap
     * thenCompose
     */
    @Test
    public void fun06() throws ExecutionException, InterruptedException {
        //第一个层future,调用了一次方法,直接返回"aaa"
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> "aaa", threadPoolExecutor);

        //future中又异步调用了一次方法(入参s拼接一个"bbb")
        CompletableFuture<CompletableFuture<String>> f2 = f1.thenApply(s -> CompletableFuture.supplyAsync(() -> s.concat("bbb"), threadPoolExecutor));
        //第一种获取值的方法
        System.out.println("f2 = " + f2.get().get());
        //使用thenCompose
        CompletableFuture<String> f3 = f1.thenCompose(s -> CompletableFuture.supplyAsync(() -> s.concat("bbb"), threadPoolExecutor));
        System.out.println("f3 = " + f3.get());

    }

    /**
     * thenCombine与thenAcceptBoth
     * thenCombine    合并两个future,对两个返回值进行处理,有返回值
     * thenAcceptBoth 同时接收两个future返回值,合并成一个future,对两个返回值进行处理,没有返回值
     * 共耗时:	3043  f1耗时2秒和f3耗时3秒
     */
    @Test
    public void fun07() throws ExecutionException, InterruptedException {
        //休眠2秒的"hello"
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> run3("hello", 2), threadPoolExecutor);

        //休眠3秒的"zgd"
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> run3("zgd", 3), threadPoolExecutor);

        //合并两个future
        CompletableFuture<String> f3 = f1.thenCombine(f2, (s, s2) -> {
            log.info("合并两个返回值:\ts1:{}\ts2:{}", s, s2);
            return s.concat(s2);
        });

        //合并两个future
        CompletableFuture<Void> f4 = f1.thenAcceptBoth(f2, (s1, s2) -> {
            log.info("s1: {}\t s2: {}",s1,s2);
        });

        log.info("f3: {}",f3.get());
        f4.get();
    }

    /**
     * applyToEither与acceptEither
     * applyToEither    取2个future中最先返回的,有返回值
     * acceptEither     取2个future中最先返回的,无返回值
     * 共耗时:	2035    拿到第一个返回的值后,程序就执行完毕了
     */
    @Test
    public void fun08() throws ExecutionException, InterruptedException {
        //休眠2秒的"hello"
        CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> run3("hello", 2), threadPoolExecutor);

        //休眠3秒的"zgd"
        CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> run3("zgd", 3), threadPoolExecutor);

        //获取最先返回的,转成大写
        CompletableFuture<String> f3 = f1.applyToEither(f2, s -> s.toUpperCase());
        System.out.println(LocalTime.now().toString() + "\tf3:最先返回的是\t" + f3.get());

        //获取最先返回的,转成大写
        CompletableFuture<Void> f4 = f1.acceptEither(f2, s -> log.info("f4:最先返回的是:\t{}", s.toUpperCase()));
        f4.get();
    }

    /**
     * allOf与anyOf
     * allOf    在一个CompletableFuture数组中,等待所有future返回
     * anyOf    在一个CompletableFuture数组中,拿到最快的future返回
     * 耗时 1053  f1.get()在第一个future返回时,就轮询结束
     */
    @Test
    public void fun11() throws ExecutionException, InterruptedException {

        //生成一个休眠时间从1到10秒的future的list
        List<CompletableFuture<String>> futures = IntStream.range(1, 11)
                .mapToObj(i -> CompletableFuture.supplyAsync(() -> run3(String.format("sleep:%d", i), i), threadPoolExecutor))
                .collect(Collectors.toList());

        CompletableFuture<Object> f1 = CompletableFuture.anyOf(futures.toArray(new CompletableFuture[]{}));

        f1.thenRun(() -> {
            log.info("最快的future已经执行完成");

            try {
                log.info("f1.get():{}", f1.get());
            } catch (Exception e) {
                e.printStackTrace();
            }

        });

        log.info("----get轮询开始----");
        f1.get();
        log.info("----get轮询结束----");

    }

    /**
     * allOf与anyOf
     * allOf    在一个CompletableFuture数组中,等待所有future返回      没有返回值
     * anyOf    在一个CompletableFuture数组中,拿到最快的future返回   有返回值
     * 耗时 10047  f1.get()在所有的futures都返回时,才执行完毕
     */
    @Test
    public void fun10() throws ExecutionException, InterruptedException {

        //生成一个休眠时间从1到10秒的future的list
        List<CompletableFuture<String>> futures = IntStream.range(1, 11)
                .mapToObj(i -> CompletableFuture.supplyAsync(() -> run3(String.format("sleep:%d", i), i), threadPoolExecutor))
                .collect(Collectors.toList());

        CompletableFuture<Void> f1 = CompletableFuture.allOf(futures.toArray(new CompletableFuture[]{}));

        f1.thenRun(() -> {
            log.info("所有的future已经执行完成");
            futures.stream().forEach(f -> {
                try {
                    log.info("result:\t{}", f.get());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });


        log.info("----get轮询开始----");
        f1.get();
        log.info("----get轮询结束----");

    }







}
