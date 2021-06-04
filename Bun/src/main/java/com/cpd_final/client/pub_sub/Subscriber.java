package com.cpd_final.client.pub_sub;

import com.cpd_final.client.ClientEntry;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class Subscriber {
    private static Logger logger = Logger.getLogger(Subscriber.class.getName());

    @RabbitListener(queues="${cpd.rabbitmq.jokesQueue}")
    public void receiveJokesMessages(String message) {
        printMessage("jokes", message);
    }

    @RabbitListener(queues="${cpd.rabbitmq.quotesQueue}")
    public void receiveQuotesMessages(String message) {
        printMessage("quotes", message);
    }

    @RabbitListener(queues="${cpd.rabbitmq.randomQueue}")
    public void receiveRandomMessages(String message) {
        printMessage("random", message);
    }

    public void printMessage(String topic, String message) {
        try {
            String[] buffer = message.split(":");

            if(buffer.length > 1 && !ClientEntry.userName.equals(buffer[0])) {
                logger.info(topic + ": " + buffer[1]);
            }
        } catch(Exception e) {
            logger.severe("Cannot parse message");
        }
    }
}
