package com.hongsheng.dto;

import lombok.Data;

@Data
public class PersonInsertDto {

    private String name;
    private String address;
    private String phone;
    private String country;
    private String provinceCode;
}
