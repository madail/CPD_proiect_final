package com.cpd_final.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientSocketHandler extends Thread{
    private Socket readSocket; //left socket
    private Socket writeSocket; //right socket
    private BufferedReader inRead;
    private PrintWriter outRead;
    private PrintWriter outWrite;
    private Integer readPort; // left port
    private Integer writePort; // right port
    private String host;
    private String token;
    private boolean canWrite; // true if the client can post messages
    private boolean doneWriting = false;
    private Integer timeLeft;
    private final Integer INTERVAL = 10;

    public ClientSocketHandler(Integer readPort, Integer writePort, String host) {
        this.readPort = readPort;
        this.writePort = writePort;
        this.host = host;
    }

    public void connect() {
        try {
            readSocket = new Socket(host, readPort);
            writeSocket = new Socket(host, writePort);
            inRead = new BufferedReader(new InputStreamReader(readSocket.getInputStream()));
            outRead = new PrintWriter(readSocket.getOutputStream(), true);
            outWrite = new PrintWriter(writeSocket.getOutputStream(), true);

            outWrite.println("P");
            outRead.println("S");
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("Cannot connect. Try again");
        }
    }

    @Override
    public void run() {
        connect();

        Scanner scanner = new Scanner(System.in);
        String message = "";
        String fromConsole = "";

        try {
            while(true) {
                System.out.println("Why " + canWrite + " " + readPort + " " + writePort);

                if(!canWrite) {
                    message = inRead.readLine();
                }

                System.out.println("Message " + message );

                if(canWrite || "GO".equals(message)) {
                    timeLeft = INTERVAL;

                    while(timeLeft > 0 && !doneWriting) {
                        outWrite.println("NOT_YET");
                        timeLeft--;
//                        fromConsole = scanner.nextLine();
//                        if ("DONE".equals(fromConsole)) {
//                            doneWriting = true;
//                        }
                        System.out.println(readPort + " " + writePort + " " + timeLeft + fromConsole);
                        Thread.sleep(1000);
                    }

                    outWrite.println("GO");
                    doneWriting = false;
                    canWrite = false;
                }

                if("DISCONNECT".equals(message)) {
                    break;
                }
            }

        } catch (IOException | InterruptedException ioException) {
            ioException.printStackTrace();
        }

        disconnect();
    }

    public void disconnect() {
        try {
            inRead.close();
            outWrite.close();
            readSocket.close();
            writeSocket.close();
        } catch(IOException ioException) {
            System.out.println("Cannot disconnect. Try again");
        }
    }

    public void setCanWrite(boolean canWrite) {
        this.canWrite = canWrite;
    }

    public void setDoneWriting(boolean doneWriting) {
        this.doneWriting = doneWriting;
    }
}
