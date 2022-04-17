package com.example.aoop_final_project;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static final String SERVER_IP = "localhost";
    static final int SERVER_PORT = 5001;

    public static void main(String[] args) throws IOException {
        InetAddress addr = InetAddress.getByName(SERVER_IP);
        Socket socket = new Socket(addr,SERVER_PORT);

        //output stream
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        //write to output stream
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input a word: ");
        String word = scanner.next();
        oos.writeObject(word);
    }

}
