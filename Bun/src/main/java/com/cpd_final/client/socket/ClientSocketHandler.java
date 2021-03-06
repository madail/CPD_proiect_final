package com.cpd_final.client.socket;

import com.cpd_final.client.ClientEntry;
import com.cpd_final.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Logger;

public class ClientSocketHandler extends Thread{
    private Socket readSocket; //left socket
    private Socket writeSocket; //right socket
    private BufferedReader inRead;
    private PrintWriter outRead;
    private PrintWriter outWrite;
    private Integer readPort; // left port
    private Integer writePort; // right port
    private String host;
    private Integer timeLeft;
    private final Integer INTERVAL = 20;
    private static Logger logger = Logger.getLogger(ClientSocketHandler.class.getName());


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
            logger.severe("Cannot connect. Try again");
        }
    }

    @Override
    public void run() {
        connect();

        String message = "";

        try {
            while(true) {
                if(!ClientEntry.canPub) {
                    message = inRead.readLine();
                }

                if(ClientEntry.canPub || "GO".equals(message)) {
                    logger.info("Writing...");
                    timeLeft = INTERVAL;

                    ClientEntry.canPub = true;
                    while(timeLeft > 0 && !ClientEntry.doneWriting) {
                        timeLeft--;
                        Thread.sleep(1000);
                    }

                    ClientEntry.canPub = false;
                    outWrite.println("GO");
                    ClientEntry.doneWriting = false;
                    logger.info("Done Writing...");
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
            logger.severe("Cannot disconnect. Try again");
        }
    }
}
