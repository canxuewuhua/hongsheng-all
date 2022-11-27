package com.hongsheng.juc.thread.multiple.futuretest;

import com.hongsheng.common.ResultDTO;
import com.hongsheng.common.ResultUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * 模拟几个运力去请求三方返回计费信息
 */
@Service
public class FutureTaskDemo {

    @Autowired
    private ThreadPoolConfig threadPoolConfig;

    /**
     * 自定义线程池
     */
    public ResultDTO handleFuture(){
        List<String> idList = Arrays.asList("fengniao","dada","shunfeng");
        List<Future<OrderPriceResponseDto>> futureArrayList = Lists.newArrayList();

        for (String id : idList){
            OrderPriceRequestDto orderPrice = new OrderPriceRequestDto();
            orderPrice.setFromLocation("111");
            orderPrice.setToLocation("222");
            orderPrice.setGoodsWeight("2.0");
            Future<OrderPriceResponseDto> submit= threadPoolConfig.submitCallable(new FutureCallableTask(id, orderPrice, this));
            futureArrayList.add(submit);
        }

        List<OrderPriceResponseDto> resultList = Lists.newArrayList();
        for (Future<OrderPriceResponseDto> future : futureArrayList) {
            try{
                OrderPriceResponseDto responseDto = future.get(8L, TimeUnit.SECONDS);
                resultList.add(responseDto);
            }catch (Exception e){
                System.out.println("xx");
            }
        }
        return ResultUtils.success(resultList);
    }

    /**
     * future 处理 如果超过规定的10秒没有返回结果就执行下一个运力逻辑了
     * 蜂鸟执行出现异常 超过了10秒 就执行下一个运力了
     * @param platform
     * @param orderPriceRequestDto
     * @return
     */
    public OrderPriceResponseDto priceCalculate(String platform, OrderPriceRequestDto orderPriceRequestDto){
        OrderPriceResponseDto orderPrice = new OrderPriceResponseDto();
        if ("fengniao".equals(platform)){
            try {
                TimeUnit.SECONDS.sleep(15);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            // 大于等于1 小于10的随机double
            String distance = String.valueOf(Math.random()*9+1);
            orderPrice.setDistance(distance);
            orderPrice.setPayFee(distance);
        }
        return orderPrice;
    }

    public void test() throws ExecutionException, InterruptedException {
        List<FutureTask<Integer>> futureList = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        while (futureList.size() > 0){
            Iterator<FutureTask<Integer>> inte = futureList.iterator();
            while (inte.hasNext()){
                Future<Integer> future = inte.next();
                if (future.isDone() && future.isCancelled()){
                    Integer i = future.get();
                    System.out.println(i);
                    list.add(i);
                    inte.remove();
                }else {
                    // 避免CPU高速轮询 可以休息一下
                    Thread.sleep(1);
                }
            }
        }
    }
}
