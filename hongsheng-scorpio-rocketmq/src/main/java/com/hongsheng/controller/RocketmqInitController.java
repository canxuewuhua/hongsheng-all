package com.hongsheng.controller;

import com.hongsheng.service.RocketmqInitService;
import com.hongsheng.service.RocketmqMsgListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/init")
public class RocketmqInitController {

    @Autowired
    private RocketmqInitService rocketmqInitService;
    @Autowired
    private RocketmqMsgListener rocketmqMsgListener;

    @GetMapping("/connect")
    public String rocketmqSend(@RequestParam("msg")String msg){
        String s = rocketmqInitService.rocketmqSend(msg);
        return s;
    }
}
