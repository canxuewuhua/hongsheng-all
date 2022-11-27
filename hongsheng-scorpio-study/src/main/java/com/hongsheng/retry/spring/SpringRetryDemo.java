package com.hongsheng.retry.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

/**
 * 注意：@Retryable失效的场景 调用方和被调用方不能在同一个类中 还有@Recover的方法的返回值要一致
 */
@Service
@Slf4j
public class SpringRetryDemo {

    /**
     * 重试所调用方法
     * @param param
     * maxAttempts 最大重试次数(包括第一次失败)，默认为3次
     */
    @Retryable(value = {RemoteAccessException.class},maxAttempts = 4,backoff = @Backoff(delay = 2000L,multiplier = 2))
    public boolean call(String param){
        return RetryDemoTask.retryTask(param);
    }

    /**
     * 达到最大重试次数,或抛出了一个没有指定进行重试的异常
     * recover 机制
     * @param e 异常
     */
    @Recover
    public boolean recover(Exception e,String param) {
        log.error("达到最大重试次数,或抛出了一个没有指定进行重试的异常:",e);
        return false;
    }

}
