package main.java;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    ServerSocket socket1;
    ServerSocket socket2;
    ServerSocket socket3;


    public void start() throws IOException {
        socket1 = new ServerSocket(5050);
        socket2 = new ServerSocket(6060);
        socket3 = new ServerSocket(7070);

        while (true) {
            System.out.println("Server");
            Socket socketForClient1 = socket1.accept();
            Socket socketForClient2 = socket2.accept();
            Socket socketForClient3 = socket3.accept();

            new ClientHandler(socketForClient1).start();
            new ClientHandler(socketForClient2).start();
            new ClientHandler(socketForClient3).start();
        }
    }

}
