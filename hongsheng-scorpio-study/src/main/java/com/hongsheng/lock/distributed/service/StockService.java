package com.hongsheng.lock.distributed.service;

import com.hongsheng.lock.distributed.pojo.Stock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.concurrent.locks.ReentrantLock;

@Service
@Slf4j
public class StockService {

    private Stock stock = new Stock();

    private ReentrantLock lock = new ReentrantLock();

    public void deduct(){
        stock.setStock(stock.getStock() - 1);
        log.info("库存余量为：{}", stock.getStock());
    }

    /**
     * 测试synchronized单机锁
     */
    public synchronized void deduct1(){
        stock.setStock(stock.getStock() - 1);
        log.info("库存余量为：{}", stock.getStock());
    }

    /**
     * 测试ReentrantLock锁
     */
    public void deduct2(){
        lock.lock();
        try {
            stock.setStock(stock.getStock() - 1);
            log.info("库存余量为：{}", stock.getStock());
        } finally {
            lock.unlock();
        }
    }
}
