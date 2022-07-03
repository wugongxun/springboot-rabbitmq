package com.wgx.springbootrabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/**
 * author:wgx
 * version:1.0
 */
@Configuration
public class TtlQueueConfig {
    //普通交换机
    public static final String X_EXCHANGE = "X";
    //死信交换机
    public static final String Y_DEAD_LETTER_EXCHANGE = "Y";
    //普通队列
    public static final String QUEUE_A = "QA";
    public static final String QUEUE_B = "QB";
    public static final String QUEUE_C = "QC";
    //死信队列
    public static final String DEAD_LETTER_QUEUE = "QD";

    //QC
    @Bean("queueC")
    public Queue queueC() {
        return QueueBuilder.durable(QUEUE_C).deadLetterExchange(Y_DEAD_LETTER_EXCHANGE).deadLetterRoutingKey("wgx").build();
    }
    @Bean
    public Binding queueCBindingX(@Qualifier("queueC") Queue queueC, @Qualifier("xExchange") DirectExchange xExchange) {
        return BindingBuilder.bind(queueC).to(xExchange).with("wgx_C");
    }

    //声明xExchange
    @Bean("xExchange")
    public DirectExchange xExchange() {
        return new DirectExchange(X_EXCHANGE);
    }
    //声明yExchange
    @Bean("yExchange")
    public DirectExchange yExchange() {
        return new DirectExchange(Y_DEAD_LETTER_EXCHANGE);
    }
    //声明队列
    @Bean("queueA")
    public Queue queueA() {
        return QueueBuilder.durable(QUEUE_A).deadLetterExchange(Y_DEAD_LETTER_EXCHANGE).deadLetterRoutingKey("wgx").ttl(10 * 1000).build();
    }
    @Bean("queueB")
    public Queue queueB() {
        return QueueBuilder.durable(QUEUE_B).deadLetterExchange(Y_DEAD_LETTER_EXCHANGE).deadLetterRoutingKey("wgx").ttl(40 * 1000).build();
    }
    @Bean("queueD")
    public Queue queueD() {
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }
    //绑定
    @Bean
    public Binding queueABindingX(@Qualifier("queueA") Queue queueA, @Qualifier("xExchange") DirectExchange xExchange) {
        return BindingBuilder.bind(queueA).to(xExchange).with("wgx_A");
    }
    @Bean
    public Binding queueBBindingX(@Qualifier("queueB") Queue queueB, @Qualifier("xExchange") DirectExchange xExchange) {
        return BindingBuilder.bind(queueB).to(xExchange).with("wgx_B");
    }
    @Bean
    public Binding queueDBindingY(@Qualifier("queueD") Queue queueD, @Qualifier("yExchange") DirectExchange yExchange) {
        return BindingBuilder.bind(queueD).to(yExchange).with("wgx");
    }
}
