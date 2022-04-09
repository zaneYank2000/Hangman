package com.example.aoop_final_project;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;


public class Server implements Runnable
{
    // Instance Variables
    private static int maxClients = 2; //max # clients
    private static final int PORT = 5001; //port #
    Scanner keyin; //scanner for input
    String myName;
    ObjectInputStream in;
    ObjectOutputStream out;
    Thread readThread, writeThread;
    boolean running;

    public Server(String[] args) throws IOException {
        if(args.length < 1) {
            System.out.println("Not enough arguments");
        } else {
            ServerSocket ss = new ServerSocket(PORT);
            myName = args[0];
            System.out.println("Hello " + myName + " waiting for client");

            try {
                Socket socket = ss.accept();
                // Set up streams
                keyin = new Scanner(System.in);
                out = new ObjectOutputStream(socket.getOutputStream());
                out.flush();
                in = new ObjectInputStream(socket.getInputStream());
                out.writeObject(myName);
                String yourName = (String) in.readObject();

                System.out.println("Communicate with " + yourName);

                // Set up and start threads
                running = true;
                readThread = new Thread(this);
                readThread.start();
                writeThread = new Thread(this);
                writeThread.start();
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server(args);
    }

    // Run methods deals with both threads and sends/receives messages
    // based on which is the current thread.
    @Override
    public void run() {
        try {
            while(running) {
                if (Thread.currentThread() == readThread) {
                    String MsgIn = (String) in.readObject();
                    System.out.println(MsgIn);
                } else if (Thread.currentThread() == writeThread) {
                    String MsgOut = keyin.nextLine();
                    out.writeObject(myName + ": " + MsgOut);
                    out.flush();
                } //end if
            } //end while
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException occurred");
            running = false;
        } catch (IOException e) {
            System.out.println("IOException occurred");
            running = false;
        } //end try catch
    } //end run
} //end class
