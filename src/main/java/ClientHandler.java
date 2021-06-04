package main.java;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler extends Thread{
    private Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;

    }

    @Override
    public void run() {
        System.out.println("Server socket " + socket.getPort());
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        String message;
        while (true) {
            try {
                message = in.readLine();
                System.out.println(message);
                if("EXIT".equals(message)) {
                    break;
                }
                out.println(message);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }
}
