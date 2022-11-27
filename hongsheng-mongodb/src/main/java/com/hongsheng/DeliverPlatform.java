package com.hongsheng;

public enum DeliverPlatform {
    SHANSONG("shansong", "闪送", 1, "4006126688"),
    DADA("dada", "达达", 4, "4001820990"),
    SHUNFENG("shunfeng", "顺丰", 5, "9533868"),
    FENGNIAO("fengniao", "蜂鸟", 2, "4008827777"),
    MEITUAN("meituan_pt", "美团跑腿", 99, "4000556100"),
    UUPT("uupt", "UU跑腿", 8, "4006997999");

    private String code;
    private String desc;
    private Integer type;
    private String phone;

    public static void main(String[] args) {
        DeliverPlatform byCode = getByCode("shansong");
        System.out.println(byCode.getType());
    }

    public static DeliverPlatform getByCode(String queryCode) {
        DeliverPlatform[] deliverPlatforms = values();
        DeliverPlatform[] var2 = deliverPlatforms;
        int var3 = deliverPlatforms.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            DeliverPlatform deliverPlatform = var2[var4];
            String code = deliverPlatform.getCode();
            if (code.equals(queryCode)) {
                return deliverPlatform;
            }
        }

        return null;
    }

    public static DeliverPlatform getByType(Integer queryCode) {
        DeliverPlatform[] deliverPlatforms = values();
        DeliverPlatform[] var2 = deliverPlatforms;
        int var3 = deliverPlatforms.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            DeliverPlatform deliverPlatform = var2[var4];
            Integer code = deliverPlatform.getType();
            if (code.equals(queryCode)) {
                return deliverPlatform;
            }
        }

        return null;
    }

    public String getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }

    public Integer getType() {
        return this.type;
    }

    public String getPhone() {
        return this.phone;
    }

    private DeliverPlatform(String code, String desc, Integer type, String phone) {
        this.code = code;
        this.desc = desc;
        this.type = type;
        this.phone = phone;
    }
}
