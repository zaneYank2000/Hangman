package com.example.aoop_final_project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {

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

        Parent root = FXMLLoader.load(getClass().getResource("popup.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Mystery Phrase");
        stage.setScene(scene);
        stage.show();

        /*create new game
        Parent root = FXMLLoader.load(getClass().getResource("game.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Hangman");
        stage.setScene(scene);
        stage.show();
         */

        //pass username and user's score in

    }

    @FXML
    void joinGameClicked(ActionEvent event) {
        //ask user what the id is to join
        //user join game
        //set user as second username and score

    }

    @FXML
    void logOutClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("newerLogin.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Welcome to Hangman!");
        stage.setScene(scene);
        stage.show();
    }

}
