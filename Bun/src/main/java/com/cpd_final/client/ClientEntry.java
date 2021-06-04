package com.cpd_final.client;

import com.cpd_final.client.pub_sub.PubSub;
import com.cpd_final.client.pub_sub.Publisher;
import com.cpd_final.client.socket.ClientSocketHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class ClientEntry {
    private static final String HOST = "127.0.0.1";
    public static boolean canPub;
    public static boolean doneWriting;
    public static String userName;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ClientEntry.class, args);

        Scanner in = new Scanner(System.in);

        System.out.println("Username: ");
        userName = in.next();
        System.out.println("Reading from port: ");
        Integer readPort = in.nextInt();
        System.out.println("Writing in port: ");
        Integer writePort = in.nextInt();
        System.out.println("Is first: ");
        canPub = in.nextBoolean();

        ClientSocketHandler socketHandler = new ClientSocketHandler(readPort, writePort, HOST);
        socketHandler.start();

        new PubSub(context.getBean(Publisher.class)).run();
    }
}
