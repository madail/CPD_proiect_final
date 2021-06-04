package com.cpd_final.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class SocketHandler extends Thread{
    private Socket socket;
    private static Socket subscriber;

    public SocketHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("Server socket " + socket.getPort());
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        try {
            String message;

            //subscriber
            if("S".equals(in.readLine())) {
                subscriber = socket;

                return;
            }

            //publisher
            while (true) {
                message = in.readLine();
                System.out.println(message);
                if("EXIT".equals(message)) {
                    break;
                }

                if(!Objects.isNull(subscriber)) {
                    if(Objects.isNull(out)) {
                        out = new PrintWriter(subscriber.getOutputStream(), true);
                    }
                    out.println(message);
                }
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        try {
            in.close();
            if(!Objects.isNull(subscriber)) {
                if(!Objects.isNull(out)) {
                    out.close();
                }
                subscriber.close();
            }
            socket.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }


}
