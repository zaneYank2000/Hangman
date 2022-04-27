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
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class LoginController
{
    File memberFile = new File("member.txt");  //stores current user
    File membersFile = new File("members.txt");  //stores all users
    FileWriter fw = new FileWriter(memberFile, true);
    PrintWriter pw = new PrintWriter(fw); // member
    FileWriter fw2 = new FileWriter(membersFile, true);
    PrintWriter pw2 = new PrintWriter(fw2); //members

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
    @FXML
    public ImageView ic;

    public LoginController() throws IOException {}

    public void Login(ActionEvent event) throws IOException {
        String user, pass;

        try {
            Scanner myReader = new Scanner(membersFile);
            while (myReader.hasNextLine()) {                      //while file still has word
                // Fetch data from file
                String player = myReader.nextLine();                //extract yser and pass line
                //System.out.println(player);
                user = player.substring(0, player.indexOf(","));
                pass = player.substring(player.indexOf(",") + 1, player.length());
                System.out.println(player + " " + user + " " + pass);

                if (user.equals("") || pass.equals("")) {
                    //tell user that username or password is incorrect
                    errorText.setText("There are no users created yet");
                    break;
                }

                // See if user exists and load menu
                if (user.equals(usernameTextField.getText()) && pass.equals(passwordPasswordField.getText())) {
                    // Save member to file
                    pw.append(user).append(",").append(pass).append("\n");
                    pw.close();

                    //allow access to menu
                    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("newerMenu.fxml")));
                    Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setTitle("Main Menu");
                    stage.setScene(scene);
                    stage.show();
                } else {
                    //tell user that username or password is incorrect
                    errorText.setText("Username or password is incorrect");
                    break;
                }
            }
            //Close the reader
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }

    public void signUpButtonClicked(ActionEvent actionEvent) throws IOException {
        // Get user and pass
        String newusername = usernameTextField.getText();
        String newpassword = passwordPasswordField.getText();

        // Add to current member text
        FileWriter fw = new FileWriter(memberFile);
        PrintWriter pw = new PrintWriter(fw);
        pw.append(newusername).append(",").append(newpassword).append("\n");
        pw.close();

        // Add to members file
        pw2.append(newusername).append(",").append(newpassword).append("\n");
        pw2.close();

        // Open the menu
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("newerMenu.fxml")));
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }
}