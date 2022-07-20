package com.hongsheng.controller;

import com.hongsheng.service.MongoDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("mongo")
public class MongoDBController {

    @Autowired
    private MongoDBService mongoDBService;

    @GetMapping("query")
    public String getMongoDBInfo(){
        mongoDBService.query();
        return "ok";
    }
}
