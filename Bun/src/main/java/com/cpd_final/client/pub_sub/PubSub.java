package com.cpd_final.client.pub_sub;

import com.cpd_final.client.ClientEntry;

import java.util.Scanner;
import java.util.logging.Logger;

public class PubSub extends Thread{
    private Publisher publisher;
    private static Logger logger = Logger.getLogger(PubSub.class.getName());

    public PubSub(Publisher publisher) {
        this.publisher = publisher;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String fromConsole = "";
        String topic = "";
        String message = "";
        String[] buffer;

        while(true) {
            if(ClientEntry.canPub) {
                fromConsole = scanner.nextLine();

                if ("DONE".equals(fromConsole)) {
                    ClientEntry.doneWriting = true;
                    ClientEntry.canPub = false;
                } else {
                    try {
                        buffer = fromConsole.split(":");

                        topic = buffer[0];
                        message = buffer[1];

                        publisher.publishMessage(ClientEntry.userName + ":" + message, topic);
                    } catch(Exception e) {
                        logger.severe("Cannot publish message. Try again.");
                    }

                }
            }
        }
    }
}
