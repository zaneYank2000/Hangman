package com.example.aoop_final_project;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    // Instance Variables
    public static final int PORT = 5001;

    // Each client will get a thread in the server so we need an
    // array of sockets and threads.
    private int MAX_USERS;
    private Socket[] users;
    private UserThread[] threads;
    private int numUsers;

    String word;
    String guess;
    boolean host = false;
    WordInformation wordInfo;

    public static void main(String[] args) throws IOException {
        Server s = new Server(2);
    }

    public Server(int m) throws IOException
    {
        MAX_USERS = m;
        users = new Socket[MAX_USERS];
        threads = new UserThread[MAX_USERS];    // Set up things and start
        numUsers = 0;                           //Server running

        try {
            runServer();
        } catch (Exception e) {
            System.out.println("Problem with Server");
        }
    }

    // Client logs off and is removed from server. Again, this is synchronized
    // so that the arrays do not become inconsistent.
    public synchronized void removeClient(int id, String name) {
        try {
            users[id].close();
        } catch (IOException e) {
            System.out.println("Already closed");
        }

        users[id] = null;
        threads[id] = null;
        // fill gap in arrays
        for (int i = id; i < numUsers - 1; i++) {
            users[i] = users[i + 1];
            threads[i] = threads[i + 1];
            threads[i].setMyId(i);
        }
        numUsers--;
        System.out.println(name + " has logged off.");
        //call send message that user left
    }

    //Method to send a message to all clients. This is synchronized to ensure
    //that a message is not interrupted by another message.
    public synchronized void SendMsg(WordInformation w) {
        for(int i = 0; i < numUsers; i++) {
            ObjectOutputStream out = threads[i].getOutputStream();
            try {
                out.writeObject(w);
                out.flush();
            } catch (IOException e) {
                System.out.println("Problem sending message to client.");
            }
        }
    }

    // Receives the messages
    private void runServer() throws IOException {
        ServerSocket s = new ServerSocket(PORT);
        System.out.println("Started: " + s);
        try {
            while(true) {
                // Ensure only 2 users are allowed at a time
                if(numUsers < MAX_USERS) {
                    try {
                        // Create a new server socket
                        Socket newSocket = s.accept();
                        System.out.println("Created a new socket in the server...");

                        // Comment
                        synchronized (this) {
                            users[numUsers] = newSocket;
                            // Get the inputstream to get the message
                            ObjectInputStream in;
                            in = new ObjectInputStream(newSocket.getInputStream());

                            // Get the name
                            //String newName = (String) in.readObject(); //the phrase????
                            WordInformation w = (WordInformation) in.readObject();
                            System.out.println(w.toString());
                            SendMsg(w);
                            System.out.println("Phrase is being sent to client: " + w.toString());
                            threads[numUsers] = new UserThread(newSocket, numUsers, w.toString(), in);
                            threads[numUsers].start();
                            System.out.println("Connection " + numUsers + users[numUsers]);
                            numUsers++;

                        }
                    } catch (IOException e) {
                        System.out.println("io exception receiving wordinfo");
                    } catch (Exception e) {
                        System.out.println("Problem with connection.......terminating");
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class UserThread extends Thread {
        private Socket mySocket;
        private ObjectInputStream myIS;
        private ObjectOutputStream myOS;
        private int myId;
        private String myName;

        private UserThread(Socket newSocket, int id, String newName, ObjectInputStream in) throws IOException {
            mySocket = newSocket;
            myId = id;
            myName = newName;
            myIS = in;
            myOS = new ObjectOutputStream(newSocket.getOutputStream());
        }

        public ObjectInputStream getInputStream() {
            return myIS;
        }

        public ObjectOutputStream getOutputStream() {
            return myOS;
        }

        public synchronized void setMyId(int newId) {
            myId = newId;
        }

        // Each UserThread will get the net=xt message from its corresponding
        // client. Each message is then sent to the other clients by the Server.
        // When there are no more messages myReader.readLine() throws an IOException.
        // Then the thread will die. D:
        public void run() {
            boolean alive = true;
            while(alive) {
                //String newMsg = null;
                try {
                    wordInfo = (WordInformation) myIS.readObject();
                    synchronized (this) {
                        Server.this.SendMsg(wordInfo);
                    }
                } catch (ClassNotFoundException e) {
                    System.out.println("Error receiving message... shutting down");
                    alive = false;
                } catch (IOException e) {
                    System.out.println("Client closing!!");
                    alive = false;
                }
            }
            removeClient(myId, myName);
        } //end run
    } //end UserThread class
} //end Server class