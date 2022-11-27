package com.hongsheng.juc.thread.multiple.completefuturetest;

import com.hongsheng.common.SmallTool;
import java.util.concurrent.TimeUnit;

public class Dish {
    // 菜名
    private String name;
    // 制作时长（秒）
    private Integer productionTime;

    public Dish(String name, Integer productionTime){
        this.name = name;
        this.productionTime = productionTime;
    }

    // 做菜
    public void mark(){
        SmallTool.sleepMillis(TimeUnit.SECONDS.toMillis(this.productionTime));
        SmallTool.printTimeAndThread(this.name + " 制作完成，来吃我吧");
    }
}
