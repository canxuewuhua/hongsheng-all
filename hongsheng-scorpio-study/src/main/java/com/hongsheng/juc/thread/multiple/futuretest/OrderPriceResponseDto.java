package com.hongsheng.juc.thread.multiple.futuretest;

import lombok.Data;
import java.io.Serializable;

@Data
public class OrderPriceResponseDto implements Serializable {
    private static final long serialVersionUID = 7750584753798328336L;

    private String payFee;
    private String distance;
}
