package com.hongsheng.juc.thread.multiple.futuretest;

import com.hongsheng.juc.thread.multiple.futuretest.FutureTaskDemo;
import com.hongsheng.juc.thread.multiple.futuretest.OrderPriceRequestDto;
import com.hongsheng.juc.thread.multiple.futuretest.OrderPriceResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.concurrent.Callable;

public class FutureCallableTask implements Callable<OrderPriceResponseDto> {

    private String storeId;
    @Autowired
    private FutureTaskDemo futureTaskDemo;
    @Autowired
    private OrderPriceRequestDto faceRequestDto;

    public FutureCallableTask(String storeId, OrderPriceRequestDto faceRequestDto, FutureTaskDemo futureTaskDemo){
        this.storeId = storeId;
        this.faceRequestDto = faceRequestDto;
        this.futureTaskDemo = futureTaskDemo;
    }

    @Override
    public OrderPriceResponseDto call() {
        OrderPriceResponseDto orderPriceResponseDto = futureTaskDemo.priceCalculate(storeId, faceRequestDto);
        return orderPriceResponseDto;
    }
}
