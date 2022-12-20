package com.hongsheng.enums;

import com.google.common.collect.Maps;
import java.util.Map;

/**
 * 蛋糕尺寸枚举
 */
public class CakeSizeEnum {

    public static final String SIZE_1 = "4";
    public static final String SIZE_2 = "6";
    public static final String SIZE_3 = "8";
    public static final String SIZE_4 = "10";
    public static final String SIZE_5 = "12";
    public static final String SIZE_6 = "14";
    public static final String SIZE_7 = "16";
    public static final String SIZE_8 = "20";
    public static final String SIZE_9 = "多层";
    public static final String SIZE_10 = "18";

    public static final Map<String, Integer> cakeSizeEnumMap = Maps.newHashMap();

    static {
        cakeSizeEnumMap.put(SIZE_1, 1);
        cakeSizeEnumMap.put(SIZE_2, 2);
        cakeSizeEnumMap.put(SIZE_3, 3);
        cakeSizeEnumMap.put(SIZE_4, 4);
        cakeSizeEnumMap.put(SIZE_5, 5);
        cakeSizeEnumMap.put(SIZE_6, 6);
        cakeSizeEnumMap.put(SIZE_7, 7);
        cakeSizeEnumMap.put(SIZE_8, 8);
        cakeSizeEnumMap.put(SIZE_9, 9);
        cakeSizeEnumMap.put(SIZE_10, 10);
    }
}
