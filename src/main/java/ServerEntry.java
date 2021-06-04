package main.java;

import java.io.IOException;

public class ServerEntry {
    public static void main(String[] args){
        try {
            Server server = new Server();
            server.start();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }
}