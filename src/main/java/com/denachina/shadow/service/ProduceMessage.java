package com.denachina.shadow.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProduceMessage {

    @Autowired
    private Queue dateQueue;

    @Autowired
    AmqpTemplate amqpTemplate;

    @RabbitListener
    public void send(String text) {
        amqpTemplate.convertAndSend(dateQueue.getName(), text);
    }

}
