package com.example.aoop_final_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Scanner;

public class LoginController {

    @FXML
    private Text errorText;

    @FXML
    private Button logInButton;

    @FXML
    private PasswordField passwordPasswordField;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField usernameTextField;

    public void Login(ActionEvent event) throws IOException {

        boolean found = false;
        String tempusername = "";
        String tempassword = "";
        Scanner scan = new Scanner("members.txt");
        scan.useDelimiter(",");


        if(usernameTextField.getText().equals("user") && passwordPasswordField.getText().equals("pass")) {

            //allow access
            Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Main Menu");
            stage.setScene(scene);
            stage.show();

        } else {

            //tell user that username or password is incorrect
            errorText.setText("Username or password is incorrect");

        }
    }

    public void signUpButtonClicked(ActionEvent actionEvent) throws IOException {
        String newusername = usernameTextField.getText();
        String newpassword = passwordPasswordField.getText();
        File wordFile = new File("members.txt");
        FileWriter fw = new FileWriter(wordFile);
        PrintWriter pw = new PrintWriter(fw);
        pw.write(newusername + "," + newpassword + "\n");
        pw.write("\n");
        pw.close();
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }
}
