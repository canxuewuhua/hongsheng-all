package com.hongsheng.lock.distributed.controller;

import com.hongsheng.lock.distributed.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StockController {
    @Autowired
    private StockService stockService;
    @GetMapping("stock/deduct")
    public String deduct(){
        stockService.deduct();
        return "hello stock deduct!!!";
    }
}
