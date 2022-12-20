package com.hongsheng.enums;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 配送状态枚举类
 */
public enum DeliverOrderStatus {
    TYPE_20(20, "生成配送单"),
    TYPE_30(30, "配送员已接单"),
    TYPE_35(35, "配送员已到店"),
    TYPE_40(40, "配送员已取货"),
    TYPE_50(50, "配送员已送达"),
    TYPE_60(60, "配送单已取消");

    private Integer code;

    private String msg;

    DeliverOrderStatus(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getCode() {
        return code;
    }

    /***
     * 根据条件获取状态枚举
     * @param notEleMap 屏蔽的元素
     * @return
     */
    public static DeliverOrderStatus[] values(Map<Integer, DeliverOrderStatus> notEleMap) {
        List<DeliverOrderStatus> deliverOrderStatusList = new ArrayList<>();
        DeliverOrderStatus[] deliverOrderStatuses = DeliverOrderStatus.values();
        for (DeliverOrderStatus deliverOrderStatus : deliverOrderStatuses) {
            DeliverOrderStatus notEle = notEleMap.get(deliverOrderStatus.getCode());
            if (notEle != null) {
                continue;
            }
            deliverOrderStatusList.add(deliverOrderStatus);
        }
        return deliverOrderStatusList.toArray(new DeliverOrderStatus[0]);
    }

    public static DeliverOrderStatus getByCode(Integer code) {
        DeliverOrderStatus[] deliverOrderStatuses = DeliverOrderStatus.values();
        for (DeliverOrderStatus deliverOrderStatus : deliverOrderStatuses) {
            if (deliverOrderStatus.getCode().equals(code)) {
                return deliverOrderStatus;
            }
        }
        return null;
    }
}
