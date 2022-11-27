package com.hongsheng.common;


import org.springframework.util.StopWatch;

public class StopWatchDTO {

    public static void main(String[] args) {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        stopWatch.stop();
        System.out.println(stopWatch.getTotalTimeMillis() + "ms");
    }
}
