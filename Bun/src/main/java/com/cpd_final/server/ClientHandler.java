package com.cpd_final.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ClientHandler extends Thread{
    private Socket socket;
    private Integer port;
    private String type; // S is subscriber, P is publisher
    private static final Map<Integer, Socket> subscriberSockets = new HashMap<>();  // list of subscribers

    public ClientHandler(Socket socket, Integer port) {
        this.socket = socket;
        this.port = port;
    }

    @Override
    public void run() {
        System.out.println("Server socket " + socket.getPort() + " from port" + port );
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        try {
            String message;
            type = in.readLine();

            //subscriber
            try {
                if("S".equals(type)) {
                    subscriberSockets.put(port, socket);

                    while(!socket.isClosed()) {
                        Thread.sleep(1000);
                    }

                    return;
                }
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }

            //publisher
            while (true) {
                message = in.readLine();
                System.out.println(message);
                if("EXIT".equals(message)) {
                    break;
                }

                if(subscriberSockets.containsKey(port)) {
                    if(Objects.isNull(out)) {
                        out = new PrintWriter(subscriberSockets.get(port).getOutputStream(), true);
                    }
                    out.println(message);
                }
            }

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        try {
            in.close();
            if(subscriberSockets.containsKey(port)) {
                if(!Objects.isNull(out)) {
                    out.close();
                }
                subscriberSockets.get(port).close();
            }
            socket.close();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

    }


}
