package com.cpd_final.client;

import java.util.Scanner;

public class PubSub extends Thread{
    private String username;

    public PubSub(String username) {
        this.username = username;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        String fromConsole = "";

        while(true) {
            if(ClientEntry.canPub) {
                fromConsole = scanner.nextLine();
                if ("DONE".equals(fromConsole)) {
                    ClientEntry.doneWriting = true;
                    ClientEntry.canPub = false;
                }
            }
        }
    }
}
