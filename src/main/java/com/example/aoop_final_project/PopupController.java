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

import java.io.*;
import java.util.Locale;


public class PopupController
{
    @FXML
    private Text errorText;
    @FXML
    private TextField wordTextField;

    @FXML
    void back(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("newerMenu.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void clearButtonClicked(ActionEvent event) {
        wordTextField.setText("");
    }

    @FXML
    void confirmButtonClicked(ActionEvent event) throws IOException, ClassNotFoundException {

        try {
            //write word to file
            File wordFile = new File("word.txt");
            FileWriter fw = new FileWriter(wordFile);
            PrintWriter pw = new PrintWriter(fw);
            String word = wordTextField.getText();
            pw.print(word.toUpperCase(Locale.ROOT).trim());
            pw.close(); 

            //if word is empty
            if(!wordTextField.getText().equals("")) {
                Parent root = FXMLLoader.load(getClass().getResource("game.fxml"));
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("Hangman");
                stage.setScene(scene);
                stage.show();
            } else {
                errorText.setText("You can not leave the text field blank");
            }
        } catch (IOException e){
            System.out.println("Error occurred.");
            e.printStackTrace();
        }
    }

}