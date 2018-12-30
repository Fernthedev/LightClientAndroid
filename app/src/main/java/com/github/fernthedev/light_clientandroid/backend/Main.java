package com.github.fernthedev.light_clientandroid.backend;

import com.github.fernthedev.client.Client;
import com.github.fernthedev.universal.StaticHandler;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    static Scanner scanner;

    static Client client;

    //private MulticastClient multicastClient;

    private String host = null;
    private int port = -1;

    private Main(String[] args) {
        new StaticHandler();

        Logger.getLogger("io.netty").setLevel(Level.OFF);

        scanner = new Scanner(System.in);


        for (String arg : args) {
            if (arg.equalsIgnoreCase("-debug")) {
                StaticHandler.isDebug = true;
            }
        }


        if(StaticHandler.isDebug) Client.getLogger().setLevel(Level.FINE);
        else Client.getLogger().setLevel(Level.INFO);



        client = new Client(host,port);
        client.initialize();
    }


    public static void main(String[] args) {
        new Main(args);
    }

    public static String readLine(String message) {
        if(!(message == null || message.equals(""))) {
            Client.getLogger().info(message);
        }
        if(scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        else return null;
    }

    public static int readInt(String message) {
        if(!(message == null || message.equals(""))) {
            Client.getLogger().info(message);
        }
        if(scanner.hasNextLine()) {
            return scanner.nextInt();
        }
        else return -1;
    }

}
