package com.cpd_final.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketHandler extends Thread{
    private ServerSocket serverSocket;
    private Integer port;

    public SocketHandler(ServerSocket serverSocket, Integer port) {
        this.serverSocket = serverSocket;
        this.port = port;
    }

    public void run() {
        try {
            while(true) {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket, port);
                clientHandler.start();
            }
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }

    }

}
