package com.wgx.springbootrabbitmq.controller;

import com.wgx.springbootrabbitmq.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * author:wgx
 * version:1.0
 */
@Slf4j
@RestController
@RequestMapping("/ttl")
public class SendMessageController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMessage/{message}")
    public void sendMessage(@PathVariable("message") String message) {
        log.info("[{}]发送一条消息:{}", new Date().toString(), message);
        rabbitTemplate.convertAndSend("X", "wgx_A", "消息来自ttl为10s的队列:" + message);
        rabbitTemplate.convertAndSend("X", "wgx_B", "消息来自ttl为40s的队列:" + message);
    }
    @GetMapping("/sendExpirationMessage/{message}/{ttlTime}")
    public void sendMessage(@PathVariable("message") String message, @PathVariable("ttlTime") String ttlTime) {
        log.info("[{}]发送一条过期时间为{}消息:{}", new Date().toString(), ttlTime, message);
        rabbitTemplate.convertAndSend("X", "wgx_C", "消息来自queueC:" + message, msg -> {
            //设置过期时间
            msg.getMessageProperties().setExpiration(ttlTime);
            return msg;
        });
    }
    @GetMapping("/sendDelayedMessage/{message}/{delayTime}")
    public void sendMessage(@PathVariable("message") String message, @PathVariable("delayTime") Integer delayedTime) {
        log.info("[{}]发送一条过期时间为{}消息到延迟队列:{}", new Date().toString(), delayedTime, message);
        rabbitTemplate.convertAndSend(DelayedQueueConfig.DELAYED_EXCHANGE,
                DelayedQueueConfig.DELAYED_ROUTING_KEY,
                message,
                msg -> {
            msg.getMessageProperties().setDelay(delayedTime);
            return msg;
                });
    }
}
