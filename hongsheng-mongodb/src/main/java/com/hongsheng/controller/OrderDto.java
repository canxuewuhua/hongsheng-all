package com.hongsheng.controller;

import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private String orderName;
    private Boolean mergeOrderFlag = Boolean.FALSE;
    private Integer orderScanFlag = 0;

    public OrderDto(Long id, String orderName) {
        this.id = id;
        this.orderName = orderName;
    }
}
