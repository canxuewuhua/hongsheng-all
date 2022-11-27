package com.hongsheng.juc.thread.multiple.futuretest;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@Component
@Slf4j
public class ThreadPoolConfig {

    ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();

    {
        log.debug("初始化线程池开始");
        int queueCount = 10000;
        poolTaskExecutor.setQueueCapacity(queueCount);
        log.info("当前线程池配置的缓存对列数量queue:{}", queueCount);
        //获取CPU核数
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        log.info("当前服务器CUP核数availableProcessors：{}", availableProcessors);
        //线程池维护线程的最小数量
        int corePoolSize = availableProcessors + 1;
        poolTaskExecutor.setCorePoolSize(corePoolSize);
        log.info("当前线程池配置的核心线程数量corePoolSize:{}", corePoolSize);
        //线程池维护线程的最大数量
        int maxPoolSize = availableProcessors * 2;
        poolTaskExecutor.setMaxPoolSize(maxPoolSize);
        log.info("当前线程池配置的最大线程数量maxPoolSize:{}", maxPoolSize);
        //空闲线程的存活时间
        poolTaskExecutor.setKeepAliveSeconds(5000);
        poolTaskExecutor.initialize();
        //如果子线程缓存队列达到上限，就会使用当前线程去执行
        poolTaskExecutor.getThreadPoolExecutor().setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        log.debug("初始化线程池结束");
    }

    public Future<OrderPriceResponseDto> submitCallable(Callable task) {
        log.info("当前活动线程数：{},核心线程数：{},总线程数：{},最大线程池数量：{},线程处理队列长度：{}", poolTaskExecutor.getActiveCount(), poolTaskExecutor.getCorePoolSize(), poolTaskExecutor.getPoolSize(), poolTaskExecutor.getMaxPoolSize(), poolTaskExecutor.getThreadPoolExecutor().getQueue().size());
        return poolTaskExecutor.submit(task);
    }

}

