package com.hongsheng.retry.guava;

import com.github.rholder.retry.*;
import com.hongsheng.common.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class GuavaRetryDemo {
    public ResultDTO guavaRetry(){
        // RetryerBuilder 构建重试实例 retryer,可以设置重试源且可以支持多个重试源，可以配置重试次数或重试超时时间，以及可以配置等待时间间隔
        Retryer<ResultDTO> retryer = RetryerBuilder.<ResultDTO> newBuilder()
                .retryIfExceptionOfType(RemoteAccessException.class)//设置异常重试源
                .retryIfResult(res-> !res.checkSuccess()) //设置根据结果重试
                // WaitStrategies参考https://blog.csdn.net/aitangyong/article/details/53889036
                //.withWaitStrategy(WaitStrategies.fixedWait(3, TimeUnit.SECONDS)) //设置等待间隔时间
                .withWaitStrategy(WaitStrategies.incrementingWait(2, TimeUnit.SECONDS,1, TimeUnit.SECONDS)) //设置等待间隔时间
                .withStopStrategy(StopStrategies.stopAfterAttempt(5)) //设置最大重试次数
                .withRetryListener(new RetryListener() {
                    @Override
                    public <V> void onRetry(Attempt<V> attempt) {
                        log.error("第【{}】次调用失败", attempt.getAttemptNumber());
                    }
                })
                .build();
        try {
            ResultDTO param = retryer.call(() -> RetryDemoTask.retryTask("param"));
            if (param.checkSuccess()){
                log.info("执行成功");
                return new ResultDTO();
            }else{
                log.error("执行失败1");
            }
        } catch (Exception e) {
            log.error("执行失败2");
        }
        return new ResultDTO(200000,"系统请求失败", null);
    }
}
