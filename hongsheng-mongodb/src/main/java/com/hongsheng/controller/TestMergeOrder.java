package com.hongsheng.controller;

import org.assertj.core.util.Lists;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestMergeOrder {

    public static void main(String[] args) {
        List<OrderDto> orderList = Arrays.asList(new OrderDto(1L, "orderA001"),
                new OrderDto(2L, "orderA002"),
                new OrderDto(3L, "orderA003"),
                new OrderDto(4L, "orderA004"),
                new OrderDto(5L, "orderA005"),
                new OrderDto(6L, "orderA006"),
                new OrderDto(7L, "orderA007"),
                new OrderDto(8L, "orderA008"),
                new OrderDto(9L, "orderA009"),
                new OrderDto(10L, "orderA0010"),
                new OrderDto(11L, "orderA0011"));
//        handleOrders(orderList, 3);
//        handleOrders(orderList, 2);
//        handleOrders(orderList, 1);
        orderList(orderList);
    }

    public static void orderList(List<OrderDto> orderList){
        for (OrderDto dto : orderList){
            dto.setOrderScanFlag(3);
        }
        orderList = orderList.stream().filter(o->!o.getOrderScanFlag().equals(3)).collect(Collectors.toList());
        System.out.println("orderList的数量：" + orderList.size());

    }

    public static void handleOrders(List<OrderDto> orderList, Integer times){
        System.out.println("times="+times);
        orderList = orderList.stream().filter(o->!o.getMergeOrderFlag()).collect(Collectors.toList());
        do{
            orderList.get(0).setMergeOrderFlag(true);
            orderList.get(1).setMergeOrderFlag(true);
            for (OrderDto dto : orderList){
                dto.setOrderScanFlag(times);
            }
        }while (orderList.stream().filter(o->!o.getOrderScanFlag().equals(times)).count()>0);
        System.out.println("while循环结束");
    }
}
