package com.example.demo.config.rabbit;


import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfig {

    @Value("${rabbitmq.payment.queue.name}")
    private String paymentQueue;
    @Value("${rabbitmq.payment.exchange.name}")
    private String paymentExchange;
    @Value("${rabbitmq.payment.key}")
    private String paymentBinding;


    @Value("${rabbitmq.payment.done.queue.name}")
    private String paymentDoneQueue;
    @Value("${rabbitmq.payment.done.exchange.name}")
    private String paymentDoneExchange;
    @Value("${rabbitmq.payment.done.key}")
    private String paymentDoneBinding;

    @Bean
    public Queue paymentQueue(){
        return new Queue(paymentQueue,false);
    }
    @Bean
    public DirectExchange paymentExchange(){
        return new DirectExchange(paymentExchange,false,false);
    }

    @Bean
    public Binding paymentBinding(){
        return BindingBuilder.bind(this.paymentQueue()).to(this.paymentExchange()).with(paymentBinding);
    }


    @Bean
    public Queue paymentDoneQueue(){
        return new Queue(paymentDoneQueue,false);
    }
    @Bean
    public TopicExchange paymentDoneExchange(){
        return new TopicExchange(paymentDoneExchange,false,false);
    }

    @Bean
    public Binding paymentDoneBinding(){
        return BindingBuilder.bind(this.paymentDoneQueue()).to(this.paymentDoneExchange()).with(paymentDoneBinding);
    }


}
