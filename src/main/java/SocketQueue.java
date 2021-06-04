package main.java;

import java.net.Socket;

public class SocketQueue {
    private Socket socket;
    private Integer id;

    public SocketQueue(Socket socket, Integer id) {
        this.socket = socket;
        this.id = id;
    }

    public Socket getSocket() {
        return socket;
    }

    public Integer getId() {
        return id;
    }
}
