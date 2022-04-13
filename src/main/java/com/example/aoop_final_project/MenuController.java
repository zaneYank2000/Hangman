package com.example.aoop_final_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Locale;
import javax.swing.*;

public class MenuController
{
    //Instance Variables
    @FXML
    private Button helpButton;
    @FXML
    private Button hostGameButton;
    @FXML
    private Button joinGameButton;
    @FXML
    private Button logOutButton;

    @FXML
    void aboutHangmanClicked(ActionEvent event) {
        //Show user rules and such about hangman
    }

    @FXML
    void hostGameClicked(ActionEvent event) throws IOException {
        /*
        Parent root = FXMLLoader.load(getClass().getResource("popup.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Mystery Phrase");
        stage.setScene(scene);
        stage.show();
        */

        String word = JOptionPane.showInputDialog("Enter a word:");
        String serverName = JOptionPane.showInputDialog("Enter the server name: ");


        try {
            InetAddress addr = InetAddress.getByName(serverName);
            Socket socket = new Socket(addr, 5001);

            //create text file and throw word into it
            File wordFile = new File("src/main/resources/com/example/aoop_final_project", "word.txt");
            FileWriter fw = new FileWriter(wordFile);
            PrintWriter pw = new PrintWriter(fw);
            pw.print(word.toUpperCase(Locale.ROOT).trim());
            pw.close();

        } catch (IOException e){
            System.out.println("Error occurred.");
            e.printStackTrace();
        }

        //create new game
        Parent root = FXMLLoader.load(getClass().getResource("game.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Hangman");
        stage.setScene(scene);
        stage.show();


        //pass username and user's score in

    }

    @FXML
    void joinGameClicked(ActionEvent event) {
        //ask user what the id is to join
        //user join game
        //set user as second username and score
        try {
            String serverName = JOptionPane.showInputDialog("Enter the server id: ");

            InetAddress addr = InetAddress.getByName(serverName);
            Socket socket = new Socket(addr, 5001);

            ObjectOutputStream myOutputStream = new ObjectOutputStream((socket.getOutputStream()));
            myOutputStream.flush();
            //Send client name to server
            myOutputStream.writeObject("test");
            myOutputStream.flush();
            ObjectInputStream myInputStream = new ObjectInputStream(socket.getInputStream());

        } catch (Exception e) {
            System.out.println("Problem starting client");
        }
    }

    @FXML
    void logOutClicked(ActionEvent event) throws IOException {
        //Clear the current user by deleting and making a new file
        File f = new File("src/main/resources/com/example/aoop_final_project/current_user.dat");
        f.delete();
        //Make the new file
        if(!(f.isFile())) {
            try {
                FileOutputStream data = new FileOutputStream("src/main/resources/com/example/aoop_final_project/current_user.dat");
                data.close();
            } catch (Exception ex) {
                System.err.println("error, could not make file");
            }
        }


        //Return to the login page
        Parent root = FXMLLoader.load(getClass().getResource("new_login.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Welcome to Hangman!");
        stage.setScene(scene);
        stage.show();
    }

}
