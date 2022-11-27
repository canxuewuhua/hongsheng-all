package com.hongsheng.juc.thread.multiple.completefuturetest;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.hongsheng.common.SmallTool;
import org.assertj.core.util.Lists;
import org.springframework.util.StopWatch;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * https://www.bilibili.com/video/BV1nA411g7d2/?spm_id_from=333.788.recommend_more_video.1&vd_source=8cb2b0b71d228eade712874ab3750f97
 * 1、小白点菜
 * 2、厨师做饭
 * 3、小白吃饭
 *
 *
 * 总结：
 * https://www.bilibili.com/video/BV1wZ4y1A7PK/?spm_id_from=pageDriver&vd_source=8cb2b0b71d228eade712874ab3750f97
 * 视频的 01：18 说区别
 * supplyAsync 开启异步任务
 * thenCompose 连接一个任务 结果有第二个任务输出
 * thenCombine 合并两个任务 有合并函数biFunction返回
 * thenApply 入参为前一个异步任务的结果 但还是同一个服务员 【用来做任务的后置处理】
 * thenApplyAsync 入参为前一个异步任务的结果 但是是不同的线程
 * applyToEither 700路或者800路 哪个线程先执行完 拿到先执行完的任务结果 最先完成的任务
 * exceptionally异常处理
 *
 *
 * xxx(arg) 是一个任务做的事
 * xxxAsync(arg) 却是两个任务做的事 和上面的是有区别的 是两个独立的任务 会将第一个任务执行完，将结果交给第二个任务
 * xxxAsync(arg,Executor)
 *
 * 详解thenCompose
 * thenCompose是将服务员A添加厨师任务的末尾
 * thenComposeAsync是将服务员A的操作当做一个独立的任务
 *
 * runAsync 不需要返回值
 * supplyAsync 需要返回结果
 *
 * thenApply 需要接收前面的结果 需要返回结果
 * thenAccept 需要接收前面的结果 不需要返回结果
 * thenRun 不需要接收参数也不需要返回结果
 *
 * thenCompose
 * 42个方法
 */
public class CompletableFutureAsync {

    public static void main(String[] args) {
        fun7();
    }

    public static void fun0(){
        SmallTool.printTimeAndThread("小白进入餐厅");
        SmallTool.printTimeAndThread("小白点了 番茄炒蛋+一碗米饭");

        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(()->{
            SmallTool.printTimeAndThread("厨师炒菜");
            SmallTool.sleepMillis(200);
            SmallTool.printTimeAndThread("服务员打饭");
            SmallTool.sleepMillis(100);
            return "番茄炒饭 + 米饭 做好了";
        });

        SmallTool.printTimeAndThread("小白在打王者");
        SmallTool.printTimeAndThread(String.format("%s ,小白开吃", cf1.join()));
    }

    /**
     * thenCompose
     * 厨师炒菜线程 完成之后 再开启一个线程执行 服务员打饭线程
     *
     * 服务员打饭 在 厨师炒菜 之后
     * thenCompose把前一个任务的结果交给后一个任务
     */
    public static void fun1() {
        SmallTool.printTimeAndThread("小白进入餐厅");
        SmallTool.printTimeAndThread("小白点了 番茄炒蛋+一碗米饭");

        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(()->{
            SmallTool.printTimeAndThread("厨师炒菜");
            SmallTool.sleepMillis(200);
            return "番茄炒饭";
        }).thenCompose(dish ->CompletableFuture.supplyAsync(()->{
            SmallTool.printTimeAndThread("服务员打饭");
            SmallTool.sleepMillis(100);
            return dish + "+ 米饭 做好了";
        }));

        SmallTool.printTimeAndThread("小白在打王者");
        SmallTool.printTimeAndThread(String.format("%s ,小白开吃", cf1.join()));
    }

    /**
     * 厨师炒菜 服务员蒸饭 两个异步任务同时进行
     * thenCombine 把上个任务和这个任务同时执行 然后一起加工
     */
    public static void fun2() {
        SmallTool.printTimeAndThread("小白进入餐厅");
        SmallTool.printTimeAndThread("小白点了 番茄炒蛋+一碗米饭");

        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(()->{
            SmallTool.printTimeAndThread("厨师炒菜");
            SmallTool.sleepMillis(200);
            return "番茄炒饭";
        }).thenCombine(CompletableFuture.supplyAsync(()->{
            SmallTool.printTimeAndThread("服务员蒸饭");
            SmallTool.sleepMillis(100);
            return "米饭";
        }),(dish,rice)->{
            SmallTool.printTimeAndThread("服务员打饭");
            SmallTool.sleepMillis(100);
            return String.format("%s + %s 好了", dish,rice);
        });

        SmallTool.printTimeAndThread("小白在打王者");
        SmallTool.printTimeAndThread(String.format("%s ,小白开吃", cf1.join()));
    }


    /**
     * 场景 小白吃完饭要开发票
     * 结账和开发票如果不是一个人
     */
    public static void fun3() {
        SmallTool.printTimeAndThread("小白吃好了");
        SmallTool.printTimeAndThread("小白 结账 要求开发票");

        CompletableFuture<String> cf1 = CompletableFuture.supplyAsync(()->{
            SmallTool.printTimeAndThread("服务员收款 500元");
            SmallTool.sleepMillis(100);

            /*
            // 开发票是另外一个人 即另一个线程去执行
            CompletableFuture<String> waiter2 = CompletableFuture.supplyAsync(()->{
                SmallTool.printTimeAndThread("服务员开发票 面额 500元");
                SmallTool.sleepMillis(200);
                return "500元发票";
            });
            return waiter2.join();*/
            return "500";
        }).thenApply(money->{
            // 入参为前一个异步任务的结果 但还是同一个服务员
            // 如果是两个服务员呢 使用thenApplyAsync 也可以换为thenCompose
            SmallTool.printTimeAndThread("服务员开发票 面额 500元");
            SmallTool.sleepMillis(200);
            return String.format("%s元发票",money);
        });

        SmallTool.printTimeAndThread("小白接到朋友电话 想一起打游戏");
        SmallTool.printTimeAndThread(String.format("小白拿到%s，准备回家", cf1.join()));
    }

    /**
     * 等公交车的案例 可以等700路 也可以等800路
     * 只需要其中的一个运行结果
     * 上个任务和这个任务一起运行 哪个先运行完成 就把哪个任务的结果 交给 Function
     * 哪个任务先执行完用哪个任务的执行结果
     */
    public static void fun4() {
        SmallTool.printTimeAndThread("小白走出餐厅 来到公交站");
        SmallTool.printTimeAndThread("等待700路 或者等待 800路 公交车到来");

        CompletableFuture<String> bus = CompletableFuture.supplyAsync(()-> {
            SmallTool.printTimeAndThread("700路公交车正在到来");
            SmallTool.sleepMillis(100);
            return "700路到了";
        }).applyToEither(CompletableFuture.supplyAsync(()-> {
            SmallTool.printTimeAndThread("800路公交车正在到来");
            SmallTool.sleepMillis(200);
            return "800路到了";
        }), firstComeBus -> firstComeBus);

        SmallTool.printTimeAndThread(String.format("%s,小白坐车回家", bus.join()));
    }

    /**
     * 链式异步 出异常的处理
     * exceptionally 可以加在尾部 也可以加到中间
     */
    public static void fun5() {
        SmallTool.printTimeAndThread("小白走出餐厅 来到公交站");
        SmallTool.printTimeAndThread("等待700路 或者等待 800路 公交车到来");

        CompletableFuture<String> bus = CompletableFuture.supplyAsync(()-> {
            SmallTool.printTimeAndThread("700路公交车正在到来");
            SmallTool.sleepMillis(100);
            return "700路到了";
        }).applyToEither(CompletableFuture.supplyAsync(()-> {
            SmallTool.printTimeAndThread("800路公交车正在到来");
            SmallTool.sleepMillis(200);
            return "800路到了";
        }), firstComeBus -> {
            SmallTool.printTimeAndThread(firstComeBus);
            if (firstComeBus.startsWith("700")){
                throw new RuntimeException("撞树了。。。");
            }
            return firstComeBus;
        }).exceptionally(e->{
            SmallTool.printTimeAndThread(e.getMessage());
            SmallTool.printTimeAndThread("小白叫了出租车");
            return "出租车 叫到了";
        });

        SmallTool.printTimeAndThread(String.format("%s,小白坐车回家", bus.join()));
    }

    /**
     * CompletableFuture for循环的情况 原始的
     * 上桌10187ms
     */
    public static void fun6(){
        SmallTool.printTimeAndThread("小白和小伙伴们 进餐厅 点菜");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Dish> dishList = new ArrayList<>();
        for (int i = 1;i<=10;i++){
            Dish dish = new Dish("菜"+i, 1);
            dishList.add(dish);
        }

        for (Dish dish : dishList){
            CompletableFuture.runAsync(()->dish.mark()).join();
        }
        stopWatch.stop();
        SmallTool.printTimeAndThread("菜都做好了，上桌" + stopWatch.getTotalTimeMillis()+"ms");
    }

    /**
     * 使用 CompletableFuture.allOf
     * 上桌2146ms
     */
    public static void fun7(){
        SmallTool.printTimeAndThread("小白和小伙伴们 进餐厅 点菜");
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        List<Dish> dishList = new ArrayList<>();

        /*
        for (int i = 1;i<=10;i++){
            Dish dish = new Dish("菜"+i, 1);
            dishList.add(dish);
        }
        List<CompletableFuture> cfList = new ArrayList<>();
        for (Dish dish : dishList){
            CompletableFuture<Void> cf = CompletableFuture.runAsync(() -> dish.mark());
            cfList.add(cf);
        }*/
        CompletableFuture[] dishes = IntStream.rangeClosed(1, 10)
                .mapToObj(i -> new Dish("菜"+i, 1))
                .map(dish -> CompletableFuture.runAsync(dish::mark))
                .toArray(size->new CompletableFuture[size]);
        CompletableFuture.allOf(dishes).join();
        stopWatch.stop();
        SmallTool.printTimeAndThread("菜都做好了，上桌" + stopWatch.getTotalTimeMillis()+"ms");
    }

    public static void fun8(){
        ExecutorService exs = Executors.newFixedThreadPool(10);
        List<CompletableFuture<String>> futureList = new ArrayList<>();
        final List<Integer> taskList = Lists.newArrayList(1,2,3,4,5,6,7,8);
        try{
            // 循环创建CompletableFuture list 调用sequence() 组装返回一个有返回值的CompletableFuture 返回结果get() 获取
            for (int i = 0; i < taskList.size(); i++) {
                int j = i;
                CompletableFuture<String> future = CompletableFuture.supplyAsync(()->calc(j),exs)
                        .thenApply(e->Integer.toString(e))
                        .whenComplete((v, e) ->{
                    System.out.println();
                });
                futureList.add(future);
            }
            List<String> strings = sequence(futureList).get();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static Integer calc(int i){
        if (i == 1){

        }
        return i;
    }

    public static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures){
        CompletableFuture<Void> allDoneFuture = CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        CompletableFuture<List<T>> listCompletableFuture = allDoneFuture.thenApply(v -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList()));
        return listCompletableFuture;
    }


}
