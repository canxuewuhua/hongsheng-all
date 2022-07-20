package com.hongsheng.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

/**
 * mongo实体类
 */
@Data
@Document(collection = "distribute_method")
public class DistributeStrategyDto {

    private ObjectId _id;
    private String storeId;
    private Integer type;
    private Method content;
    private Date createTime;
    private Date updateTime;

    public interface Method {
        int[] getPlatforms();
    }

    @Data
    public static class Manual implements Method {
        private int[] distributePlatforms;

        @Override
        public int[] getPlatforms() {
            return distributePlatforms;
        }
    }

    @Data
    public static class Automatic implements Method {

        private int[] distributePlatforms;

        private int[] orderPlatforms;

        @Override
        public int[] getPlatforms() {
            return orderPlatforms;
        }
    }

    /***
     * 获取自动配送的信息
     * @return
     */
    public Automatic getAutomatic() {
        return (Automatic) content;
    }

    /***
     * 获取手动配送信息
     * @return
     */
    public Manual getManual() {
        return (Manual) content;
    }
}
