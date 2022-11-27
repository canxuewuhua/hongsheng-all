package com.hongsheng.retry.guava;

import com.hongsheng.common.ResultDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.remoting.RemoteAccessException;

/**
 * 可以根据返回的结果来判断是否重试 推荐
 */
@Slf4j
public class RetryDemoTask {


    /**
     * 重试方法
     * @return
     */
    public static ResultDTO retryTask(String param)  {
        log.info("收到请求参数:{}",param);

        int i = RandomUtils.nextInt(6,11);
//        int i = 4;
        log.info("随机生成的数:{}",i);
        if (i < 2) {
            log.info("为0,抛出参数异常.");
            throw new IllegalArgumentException("参数异常");
        }else if (i < 5){
            log.info("为1,返回true.");
            return new ResultDTO();
        }else if (i < 7){
            log.info("为2,返回false.");
            return new ResultDTO(200000, "系统请求失败", null);
        }else{
            //为其他
            log.info("大于2,抛出自定义异常.");
            throw new RemoteAccessException("大于2,抛出自定义异常");
        }
    }

}
