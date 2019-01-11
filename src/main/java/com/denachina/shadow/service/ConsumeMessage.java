package com.denachina.shadow.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumeMessage {

    private static final Logger logger = LoggerFactory.getLogger(ConsumeMessage.class);

    @Autowired
    AmqpTemplate amqpTemplate;

    @RabbitListener(queues = "QueryTime")
    public void reciveMessage(String message) {
        logger.info("Received message Queue Name QueryTime <" + message + ">");
    }

}
