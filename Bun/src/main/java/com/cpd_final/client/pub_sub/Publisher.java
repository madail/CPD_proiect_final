package com.cpd_final.client.pub_sub;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class Publisher {
    private static Logger logger = Logger.getLogger(Publisher.class.getName());

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void publishMessage(String message, String topic){
        amqpTemplate.convertAndSend(topic, "", message);
        logger.info("Send msg = " + message);
    }
}
