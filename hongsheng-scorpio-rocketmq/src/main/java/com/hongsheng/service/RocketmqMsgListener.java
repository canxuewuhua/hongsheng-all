package com.hongsheng.service;

import com.alibaba.fastjson.JSON;
import com.hongsheng.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RocketMQMessageListener(
        consumerGroup = "consumer-group",
        topic = "second-topic")
public class RocketmqMsgListener implements RocketMQListener<MessageDto> {
    @Override
    public void onMessage(MessageDto messageDto) {
        log.warn("消费到消息 => "+ messageDto.toString());
    }
}
