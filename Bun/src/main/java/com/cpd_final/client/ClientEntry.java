package com.cpd_final.client;

import java.util.Scanner;

public class ClientEntry {
    private static String HOST = "127.0.0.1";
    public static boolean canPub;
    public static boolean doneWriting;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Username: ");
        String username = in.next();
        System.out.println("Reading from port: ");
        Integer readPort = in.nextInt();
        System.out.println("Writing in port: ");
        Integer writePort = in.nextInt();
        System.out.println("Is first: ");
        canPub = in.nextBoolean();

        System.out.println(readPort + " " + writePort + " " + canPub);

        ClientSocketHandler socketHandler = new ClientSocketHandler(readPort, writePort, HOST);
        socketHandler.start();

        new PubSub(username).run();
    }
}
