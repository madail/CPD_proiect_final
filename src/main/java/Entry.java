package main.java;

import java.util.Scanner;

public class Entry {
    private static String HOST = "127.0.0.1";
    private static boolean canPost = false;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        System.out.println("Reading from port: ");
        Integer readPort = in.nextInt();
        System.out.println("Writing in port: ");
        Integer writePort = in.nextInt();
        System.out.println("Is first: ");
        boolean isFirst = in.nextBoolean();

        System.out.println(readPort + " " + writePort + " " + isFirst);

        SocketHandler socketHandler = new SocketHandler(readPort, writePort, HOST);
        socketHandler.setCanWrite(isFirst);
        socketHandler.start();
    }
}
