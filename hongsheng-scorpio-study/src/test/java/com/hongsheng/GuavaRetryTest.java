package com.hongsheng;

import com.alibaba.fastjson.JSON;
import com.github.rholder.retry.*;
import com.hongsheng.common.ResultDTO;
import com.hongsheng.retry.guava.GuavaRetryDemo;
import com.hongsheng.retry.guava.RetryDemoTask;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.RemoteAccessException;
import java.util.concurrent.TimeUnit;

/**
 * https://mp.weixin.qq.com/s/lHDhQqVLoP3g98ExzGkl8g
 *
 * spring-retry 和 guava-retry 工具都是线程安全的重试，能够支持并发业务场景的重试逻辑正确性。
 * 两者都很好的将正常方法和重试方法进行了解耦,可以设置超时时间,重试次数,间隔时间,监听结果,都是不错的框架
 *
 * 但是明显感觉得到,guava-retry在使用上更便捷,更灵活,能根据方法返回值来判断是否重试,而Spring-retry只能根据抛出的异常来进行重试
 */
@Slf4j
public class GuavaRetryTest extends MyBaseTest{

    @Autowired
    private GuavaRetryDemo guavaRetryDemo;

    @Test
    public void testGuavaRetry(){
        ResultDTO resultDTO = guavaRetryDemo.guavaRetry();
        log.info("resultDTO:{}", JSON.toJSONString(resultDTO));
    }

}
