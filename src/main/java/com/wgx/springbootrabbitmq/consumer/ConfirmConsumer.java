package com.wgx.springbootrabbitmq.consumer;

import com.wgx.springbootrabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * author:wgx
 * version:1.0
 */
@Slf4j
@Component
public class ConfirmConsumer {
    @RabbitListener(queues = ConfirmConfig.CONFIRM_QUEUE)
    public void receiveMessage(Message message) {
        String msg = new String(message.getBody());
        log.info("收到消息{}", msg);
    }
}
