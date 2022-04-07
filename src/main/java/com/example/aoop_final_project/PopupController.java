package com.example.aoop_final_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;


public class PopupController {
    public ArrayList wordArray = new ArrayList<>();

    @FXML
    private Text errorText;

    @FXML
    private TextField wordTextField;

    @FXML
    void clearButtonClicked(ActionEvent event) {
        wordTextField.setText("");
    }

    @FXML
    void confirmButtonClicked(ActionEvent event) throws IOException {
        if(1==1) {
            Parent root = FXMLLoader.load(getClass().getResource("game.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Hangman");
            stage.setScene(scene);
            stage.show();
        } else {
            errorText.setText("You can not leave the text field blank");
        }

    }
}

