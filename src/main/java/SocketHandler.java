package main.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class SocketHandler extends Thread{
    private ServerSocket readSocket; //left socket
    private Socket writeSocket; //right socket
    private BufferedReader in;
    private PrintWriter out;
    private Integer readPort; // left port
    private Integer writePort; // right port
    private String host;
    private String token;
    private boolean canWrite; // true if the client can post messages
    private boolean doneWriting = false;
    private Integer timeLeft;
    private final Integer INTERVAL = 10;

    public SocketHandler(Integer readPort, Integer writePort, String host) {
        this.readPort = readPort;
        this.writePort = writePort;
        this.host = host;
    }

    public void connect() {
        try {
            readSocket = new java.net.ServerSocket(readPort);
            writeSocket = new java.net.Socket(host, writePort);
            in = new BufferedReader(new InputStreamReader(readSocket.accept().getInputStream())); //aici se opreste somehow
            out = new PrintWriter(writeSocket.getOutputStream(), true);
        } catch (IOException ioException) {
            ioException.printStackTrace();
            System.out.println("Cannot connect. Try again");
        }
    }
//si acum cred ca trebuie sa fac aia

    @Override
    public void run() {
        connect();

        Scanner scanner = new Scanner(System.in);
        String message;

        while(true) {
            try {
                System.out.println("Why");

                message = in.readLine();

                if(canWrite || "GO".equals(message)) {
                    timeLeft = INTERVAL;

                    while(timeLeft > 0 && !doneWriting) {
                        out.println("NOT_YET");
                        timeLeft--;
                        scanner.nextLine();
                        if ("DONE".equals(scanner.nextLine())) {
                            doneWriting = true;
                        }
                        System.out.println(readPort + " " + writePort + " " + timeLeft);
                    }

                    out.println("GO");
                    doneWriting = false;
                    canWrite = false;
                }

                if("DISCONNECT".equals(message)) {
                    break;
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        //ma gandeam sa mai modific si eu de la tema cu airbnb dar se pare ca modificat nu merge :)))
        disconnect();
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

    public void setCanWrite(boolean canWrite) {
        this.canWrite = canWrite;
    }

    public void setDoneWriting(boolean doneWriting) {
        this.doneWriting = doneWriting;
    }
}
