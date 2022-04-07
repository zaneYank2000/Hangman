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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class PopupController {

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
        try {
            File wordFile = new File("src/main/resources/com/example/aoop_final_project", "word.txt");
            FileWriter fw = new FileWriter(wordFile);
            PrintWriter pw = new PrintWriter(fw);
            String word = wordTextField.getText();
            pw.print(word);
            pw.close();
        } catch (IOException e){
            System.out.println("Error occurred.");
            e.printStackTrace();
        }



        //TODO: FIX THIS IF STATEMENT
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

