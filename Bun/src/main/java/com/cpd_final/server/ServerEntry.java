package com.cpd_final.server;

import java.util.Scanner;

public class ServerEntry {
    public static void main(String[] args){
        Scanner in = new Scanner(System.in);

        System.out.println("port: ");
        Integer port = in.nextInt();

        Server server = new Server();
        server.start(port);
    }
}
