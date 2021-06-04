package com.cpd_final.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    ServerSocket socket1;
    ServerSocket socket2;
    ServerSocket socket3;

    public void start() {
        try{
            socket2 = new ServerSocket(6060);
            socket1 = new ServerSocket(5050);
            socket3 = new ServerSocket(7070);

            new SocketHandler(socket2, 6060).run();
            new SocketHandler(socket1, 5050).run();
            new SocketHandler(socket3, 7070).run();

        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }
}
