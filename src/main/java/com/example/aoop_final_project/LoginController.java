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

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class LoginController extends Component {
    //Instance Variables
    @FXML
    private Button logInButton;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private Button signUpButton;
    @FXML
    private TextField usernameTextField;

    public void Login(ActionEvent event) throws IOException {
        String user = usernameTextField.getText();
        String pass = passwordPasswordField.getText();

        if(user.trim().equals("") || pass.trim().equals("")) {
            // Error message
            JOptionPane.showMessageDialog(null, "Username or password are empty.");
            return;
        }

        File usersFile = new File("src/main/resources/com/example/aoop_final_project/users.dat");

        if (usersFile.exists()) {
            try {
                FileInputStream fin = new FileInputStream(usersFile);
                ObjectInputStream ois = new ObjectInputStream(fin);

                //TODO: Make sure we can have more than 5 users
                for (int i = 0; i < 5; i++) {   //Note: only allows to store 5 different users
                    //Get a user from the users.dat file
                    User test = (User)ois.readObject();
                    String username = test.getUser();
                    String password = test.getPass();

                    // Check entered strings against user
                    if (user.equals(username) && pass.equals(password)) {
                        //Add to current user
                        FileOutputStream fos = new FileOutputStream(new File("src/main/resources/com/example/aoop_final_project/current_user.dat"));
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(user);

                        //Close streams
                        oos.close();
                        fos.close();

                        //Send to menu cuz right login info
                        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
                        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setTitle("Main Menu");
                        stage.setScene(scene);
                        stage.show();
                    }
                }
                ois.close();
                fin.close();

                // Error message
                JOptionPane.showMessageDialog(null, "Username or password are incorrect.");
                return;
            } catch (FileNotFoundException e) {
                System.err.println("No file exists to read from");
            } catch (EOFException e) {
                System.err.println("Reached the end of the users file");
            } catch (IOException e) {
                System.err.println("IO err while checking the users file");
            } catch (Exception e) {
                System.err.println("Exception while checking the users file");
            }


        }
    }

    public void signUp(ActionEvent event) throws IOException {
        String user = usernameTextField.getText();
        String pass = passwordPasswordField.getText();

        if(user.trim().equals("") || pass.trim().equals("")) {
            // Error message
            JOptionPane.showMessageDialog(null, "Username or password are empty.");
            return;
        }

        // Confirm is the user wanted their username and password
        int response = JOptionPane.showConfirmDialog(
                null,
                String.format("Are you sure you would like to sign up with:\nUser Name: " + user + "\nPassword: " + pass),
                "Confirm Selection",
                JOptionPane.YES_NO_OPTION
        );

        if(response == JOptionPane.YES_OPTION) {
            // Create a new user object with the given data
            User newUser = new User(user, pass);

            //Save to dat file
            if(response == JOptionPane.YES_OPTION) {
                File users = new File("src/main/resources/com/example/aoop_final_project/users.dat");
                File currentUSer = new File("src/main/resources/com/example/aoop_final_project/current_user.dat");

                if (users.exists()) {
                    try {
                        FileOutputStream fis = new FileOutputStream(users);
                        ObjectOutputStream ois = new ObjectOutputStream(fis);
                        FileOutputStream fis1 = new FileOutputStream(currentUSer);
                        ObjectOutputStream ois1 = new ObjectOutputStream(fis1);

                        //Add players from player.dat to playerList
                        ois.writeObject(newUser);
                        ois1.writeObject(newUser);

                        //Close streams
                        fis.close();
                        ois.close();
                        fis1.close();
                        ois1.close();
                    } catch (FileNotFoundException ex) {
                        System.err.println("Couldn't find the user file to write to.");
                    } catch (EOFException ex) {
                        System.err.println("Reached end of file adding new user.");
                    } catch (IOException ex) {
                        System.err.println("IOException adding new user to file.");
                    } catch (Exception ex) {
                        System.err.println("Exception adding new user to file.");
                    }
                }
            }

            // Open the menu screen
            Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Main Menu");
            stage.setScene(scene);
            stage.show();

            //TODO: Connect to the server
        }
    }
}
