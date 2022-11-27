package com.hongsheng.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hongsheng.annotation.Excel;
import lombok.Data;

@Data
@TableName("personal")
public class Personal {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Excel(name = "用户姓名")
    @TableField("name")
    private String name;

    @Excel(name = "用户地址")
    @TableField("addr")
    private String address;

    @Excel(name = "国家")
    @TableField("country")
    private String country;

    @Excel(name = "省份拼音")
    @TableField("province_code")
    private String provinceCode;
}
