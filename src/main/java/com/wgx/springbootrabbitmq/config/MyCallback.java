package com.wgx.springbootrabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * author:wgx
 * version:1.0
 */
@Slf4j
@Component
public class MyCallback implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //注入
    @PostConstruct
    public void init() {
        rabbitTemplate.setReturnsCallback(this::returnedMessage);
        rabbitTemplate.setConfirmCallback(this::confirm);
    }

    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.error("消息[{}]被回退了,交换机[{}],routingkey[{}],回退原因[{}]",
                new String(returned.getMessage().getBody()),
                returned.getExchange(),
                returned.getRoutingKey(),
                returned.getReplyText());
    }

    /**
     * 交换机确认回调方法
     * @param correlationData 保存回调消息的ID及相关信息
     * @param ack 交换机接受到消息ack=true，没有接受到消息ack=false
     * @param cause 交换机接受成功为null，失败为失败的原因
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        String id = (correlationData != null) ? correlationData.getId() : "";
        if (ack) {
            log.info("交换机收到id为{}的消息", id);
        } else {
            log.info("因为{}没有收到id为{}消息", cause, id);
        }
    }
}
