package com.hongsheng.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("map")
@Slf4j
public class MapController {

    public void testMapUrl(){
        log.info("test map url");
    }

    public static void main(String[] args) {
        double d = 116472173;
        int m = (int)d;
        int i = new Double(d).intValue();
        System.out.println(i);
    }
}
