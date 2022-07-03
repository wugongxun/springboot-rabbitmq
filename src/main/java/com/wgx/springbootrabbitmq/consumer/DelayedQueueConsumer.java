package com.wgx.springbootrabbitmq.consumer;

import com.wgx.springbootrabbitmq.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
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
public class DelayedQueueConsumer {
    @RabbitListener(queues = DelayedQueueConfig.DELAYED_QUEUE)
    public void receiveDelayedQueue(@NotNull Message message) {
        String msg = new String(message.getBody());
        log.info("[{}]收到延迟队列消息:{}", new Date().toString(), msg);
    }
}
