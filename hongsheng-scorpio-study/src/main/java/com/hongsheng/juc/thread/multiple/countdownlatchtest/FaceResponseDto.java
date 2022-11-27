package com.hongsheng.juc.thread.multiple.countdownlatchtest;

import lombok.Data;

@Data
public class FaceResponseDto {

    private String userId;
    private String transDate;

    public FaceResponseDto(){

    }

    public FaceResponseDto(String userId, String transDate){
        this.userId = userId;
        this.transDate = transDate;
    }
}
