package com.hongsheng.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("distribute_method")
public class DistributeMethod {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("store_id")
    private String merchantId;
    private String distributePlatforms;
    private String orderPlatforms;
    private Date createTime;
    private Date updateTime;
}
