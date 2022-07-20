package com.hongsheng.entity;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("user")
@Data
public class User {

    private ObjectId _id;
    private String name;
    private Double salary;
    private Date birthday;
}
