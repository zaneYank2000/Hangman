package com.example.aoop_final_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class MenuController
{
    @FXML
    void aboutHangmanClicked(ActionEvent event) {
        //TODO:
    }

    @FXML
    void hostGameClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("popup.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Mystery Phrase");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void joinGameClicked(ActionEvent event) throws IOException {
        //ask user what the id is to join
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("guestGame.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Welcome to Hangman!");
        stage.setScene(scene);
        stage.show();
    }

    //Logs out and returns to menu
    @FXML
    void logOutClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("newerLogin.fxml")));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Welcome to Hangman!");
        stage.setScene(scene);
        stage.show();
    }

}
