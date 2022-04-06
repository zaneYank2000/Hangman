package com.example.aoop_final_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.IOException;

public class GameController {

    @FXML
    private Line armLeft;

    @FXML
    private Line armRight;

    @FXML
    private Line body;

    @FXML
    private Label gameID;

    @FXML
    private TextField guessTextField;

    @FXML
    private Circle head;

    @FXML
    private Line legLeft;

    @FXML
    private Line legRight;

    @FXML
    private Label stringLabel;

    @FXML
    private Label user1Score;

    @FXML
    private Label user2Score;

    @FXML
    private Label username1;

    @FXML
    private Label username2;

    @FXML
    private Label warningLabel;

    /* TRYING TO ADD REGEX
    @FXML
    public void initialize() {
        guessTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[A-Za-z]$")) {
                guessTextField.setText(newValue.replaceAll("[^[A-Za-z]$]", ""));
            }
        });
    }
     */

    @FXML
    void exitButtonClicked(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void submitButtonClicked(ActionEvent event) {
        System.out.println("Submit clicked");
    }

}