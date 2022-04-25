package com.example.aoop_final_project;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server{
    private static final int PORT = 5001;
    static ObjectOutputStream oos, oos2;
    static ObjectInputStream ois, ois2;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket ss = new ServerSocket(PORT);
        System.out.println("SERVER is waiting for client connection");
        ServerSocket ss2 = new ServerSocket(5002);

        Socket socket = ss.accept();
        System.out.println("CLIENT has been connected");

        //receive message from client
        ois = new ObjectInputStream(socket.getInputStream());

        //get message from client and print to console
        String word = (String) ois.readObject();
        System.out.println("Server has received " + word + " form client");

        //send word back to client
        oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(word);
        oos.flush();
        System.out.println("Sending the word " + word + " back to client");

        //get information to second player
        Socket socket2 = ss2.accept();
        System.out.println("Second CLIENT has been connected");
        oos2 = new ObjectOutputStream(socket2.getOutputStream());
        oos2.writeObject(word);
        System.out.println("Sending " + word + " to client 2");
        ois2 = new ObjectInputStream(socket2.getInputStream());


        MyThread t1 = new MyThread();
        System.out.println("About to start thread");
        t1.start();
        System.out.println("thread is running");

    }


    private static class MyThread extends Thread {
        @Override
        public void run()
        {
            boolean alive = true;
            while (alive)
            {
                System.out.println("WE ARE NOW IN WHILE LOOP");

                try {
                    System.out.println("IN TRY BLOCK");
                    String newMsg = "";
                    newMsg = (String) ois2.readObject();
                    System.out.println("just got the word: " + newMsg);
                    synchronized(this)
                    {
                        System.out.println("About to send:" + newMsg);
                        oos.writeObject(newMsg);
                        oos.flush();
                        System.out.println("Word in oos is now " + newMsg);
                    }
                }
                catch (ClassNotFoundException e)  {
                    System.out.println("Error receiving message....shutting down");
                    alive =false;
                    e.printStackTrace();
                }
                catch (IOException e)
                {
                    System.out.println("Client closing!!");
                    alive = false;
                    e.printStackTrace();
                }
            }
        }
    }

}


