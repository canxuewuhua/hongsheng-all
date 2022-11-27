package com.hongsheng.juc.thread.multiple.futuretest;

import lombok.Data;
import java.io.Serializable;

@Data
public class OrderPriceRequestDto implements Serializable {
    private static final long serialVersionUID = -5156280573745088178L;

    private String fromLocation;
    private String toLocation;
    private String goodsWeight;
}
