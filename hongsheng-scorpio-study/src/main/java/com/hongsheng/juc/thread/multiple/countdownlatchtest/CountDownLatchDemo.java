package com.hongsheng.juc.thread.multiple.countdownlatchtest;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.hongsheng.juc.thread.multiple.futuretest.OrderPriceRequestDto;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@Service
public class CountDownLatchDemo {

    ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("thread-%d").build();
    ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(30,30,0L, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(1024), threadFactory);


    /**
     * 每次执行完一个线程减一
     * @return
     * @throws InterruptedException
     */
    public List<FaceResponseDto> handleList(OrderPriceRequestDto faceRequestDto, String userId) throws InterruptedException {
        List<String> idList = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(idList.size());
        for (String id : idList){
            try{
                threadPoolExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        handleResultList(faceRequestDto, userId);
                    }
                });
            }catch (Exception e){
                System.out.println("多线程执行出现错误，错误信息="+ e.getMessage());
            }
        }
        latch.await();
        return new ArrayList<FaceResponseDto>();
    }

    public List<FaceResponseDto> handleResultList(OrderPriceRequestDto faceRequestDto, String userId){
        return new ArrayList<FaceResponseDto>();
    }
}
