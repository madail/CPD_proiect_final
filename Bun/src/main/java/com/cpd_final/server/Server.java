package com.cpd_final.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    ServerSocket socket;

    public void start(Integer port) {
        try{
            socket = new ServerSocket(port);

            new SocketHandler(socket, port).run();

        } catch(IOException exception) {
            exception.printStackTrace();
        }
    }
}
