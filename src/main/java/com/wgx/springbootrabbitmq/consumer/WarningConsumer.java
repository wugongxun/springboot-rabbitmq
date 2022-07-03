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
public class WarningConsumer {
    @RabbitListener(queues = ConfirmConfig.WARNING_QUEUE)
    public void receiveMessage(Message message) {
        String msg = new String(message.getBody());
        log.error("检测到一条不可路由的消息:{}", msg);
    }
}
