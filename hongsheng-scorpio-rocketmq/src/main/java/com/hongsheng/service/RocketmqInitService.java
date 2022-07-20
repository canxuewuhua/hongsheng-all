package com.hongsheng.service;

import com.hongsheng.dto.MessageDto;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RocketmqInitService {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    public String rocketmqSend(String msg){
        MessageDto.MessageDtoBuilder builder = MessageDto.builder();
        builder.msgId("JK019303440000A2")
               .msgContent(msg);
        System.out.println(builder);
        rocketMQTemplate.convertAndSend("second-topic:tag001", builder.build());
        return "投递消息 => " + msg + " => 成功";
    }

}
