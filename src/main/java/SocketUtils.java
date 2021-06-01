package main.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketUtils{
    private Socket readSocket; //left socket
    private Socket writeSocket; //right socket
    private BufferedReader in;
    private PrintWriter out;
    private Integer readPort; // left port
    private Integer writePort; // right port
    private String host;
    private String token;
    private boolean canWrite; // true if the client can post messages
    private SocketUtils socketInstance;

    private SocketUtils() { }

    public SocketUtils getSocketInstance() {
        if (null == socketInstance) {
            socketInstance = new SocketUtils();
        }

        return socketInstance;
    }

    public void connect() {
        try {
            readSocket = new java.net.Socket(host, readPort);
            writeSocket = new java.net.Socket(host, writePort);
            in = new BufferedReader(new InputStreamReader(readSocket.getInputStream()));
            out = new PrintWriter(writeSocket.getOutputStream(), true);
        } catch (IOException ioException) {
            System.out.println("Cannot connect. Try again");
        }
    }

    public void disconnect() {
        try {
            in.close();
            out.close();
            readSocket.close();
            writeSocket.close();
        } catch(IOException ioException) {
            System.out.println("Cannot disconnect. Try again");
        }
    }
}
