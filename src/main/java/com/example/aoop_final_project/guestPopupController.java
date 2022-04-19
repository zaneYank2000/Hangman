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
import java.net.Socket;

public class guestPopupController {


    @FXML
    private Text errorText;

    @FXML
    private TextField wordTextField;

    @FXML
    void clearButtonClicked(ActionEvent event) {
        wordTextField.setText("");
    }

    @FXML
    void confirmButtonClicked(ActionEvent event) throws IOException, ClassNotFoundException {

        try {
            String SERVER_IP = wordTextField.getText();
            System.out.println("Client has just inputted: " + SERVER_IP);
            Socket socket = new Socket(SERVER_IP, 5002);
            System.out.println("SOCKET HAS BEEN MADE");

            //if word is not empty
            if(!wordTextField.getText().equals("")) {
                Parent root = FXMLLoader.load(getClass().getResource("guestGame.fxml"));
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
