package com.hongsheng.entity;

import lombok.Data;

@Data
public class Product {
//    @Id
    private Integer id;
//    @Field(type = FieldType.Keyword)
    private String title;
//    @Field(type = FieldType.Float)
    private Double price;
//    @Field(type = FieldType.Text)
    private String description;
    //get set ...
}
