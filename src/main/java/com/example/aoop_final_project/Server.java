package com.example.aoop_final_project;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Locale;

public class Server {
    private static final int PORT = 5001;


    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket ss = new ServerSocket(PORT);
        System.out.println("SERVER is waiting for client connection");

        Socket socket = ss.accept();
        System.out.println("CLIENT has been connected");

        //receive message from client
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

        //get message from client and print to console
        String word = (String) ois.readObject();
        System.out.println("Server has received " + word + " form client");

        //send word back to client
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(word);

    }

}
