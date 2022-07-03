package com.wgx.springbootrabbitmq.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * author:wgx
 * version:1.0
 */
@Slf4j
@Component
public class DeadLetterQueueConsumer {
    @RabbitListener(queues = "QD")
    public void receiver(Message message, Channel channel) {
        String msg = new String(message.getBody());
        log.info("[{}]收到死信队列消息:{}", new Date().toString(), msg);
    }
}
