package com.hongsheng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class SpringStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringStudyApplication.class, args);
    }
}
