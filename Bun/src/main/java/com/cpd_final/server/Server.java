package com.cpd_final.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    public void start(Integer port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while(true) {
                Socket socket = serverSocket.accept();
                SocketHandler socketHandler = new SocketHandler(socket);
                socketHandler.start();
            }
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
