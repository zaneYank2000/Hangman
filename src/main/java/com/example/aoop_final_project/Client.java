package com.example.aoop_final_project;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable
{
    // Instance Variables
    static final int PORT = 5001;
    Thread readThread, writeThread;
    ObjectInputStream in;        // for messages from client
    Scanner keyin;    // for messages typed from keyboard
    ObjectOutputStream out;          // for messages to client
    Socket socket;
    String myName;
    boolean running;

    public Client (String [] args)
    {
        try {
            if (args.length < 2) {
                System.out.println("Not enough arguments...terminating");
            } else {
                InetAddress addr = InetAddress.getByName(args[0]);
                socket = new Socket(addr, PORT);
                keyin = new Scanner(System.in);
                in = new ObjectInputStream(socket.getInputStream());
                out = new ObjectOutputStream(socket.getOutputStream());
                out.flush();
                myName = args[1];

                String yourName = (String)in.readObject();
                out.writeObject(myName);

                System.out.println("Communicate with " + yourName);
                System.out.println("Type Bye to end");
                running = true;
                readThread = new Thread(this);
                readThread.start();
                writeThread = new Thread(this);
                writeThread.start();
            }
        } catch( Exception e) {
            System.out.println("Problem connecting");
        }
    }
    public void run()
    {
        String MsgOut = new String("");
        String MsgIn = new String("");
        try {
            while ((running) && (!MsgOut.equals("Bye"))) {
                if (Thread.currentThread() == readThread) {
                    MsgIn = (String) in.readObject();
                    System.out.println(MsgIn);
                } else if (Thread.currentThread() == writeThread) {
                    MsgOut = keyin.nextLine();
                    out.writeObject(myName + ": " + MsgOut);
                    out.flush();
                }
            }
            running = false;
            socket.close();

        } catch (ClassNotFoundException e)  {
            System.out.println("Error receiving message....shutting down");
            running = false;
        } catch (IOException e) {
            System.out.println("Shutting down");
            running = false;
        }
    }

    public static void main( String [] args) {
        Client C = new Client(args);
    }
}

