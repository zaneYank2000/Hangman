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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GameController {

    @FXML
    private Line armLeft;

    @FXML
    private Line armRight;

    @FXML
    private Line body;

    @FXML
    private Label displayLabel;

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
    private Label user1Score;

    @FXML
    private Label user2Score;

    @FXML
    private Label username1;

    @FXML
    private Label username2;

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


    public void initialize() {
        ArrayList wordArray = new ArrayList();
        ArrayList wordBlank = new ArrayList();

        //read from 'word.txt' and save it to an ArrayList
        try {
            File myObj = new File("src/main/resources/com/example/aoop_final_project", "word.txt");
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {                  //while file still has word
                String word = myReader.nextLine();            //extract word
                for (int i = 0; i < word.length(); i++) {
                    wordArray.add(word.charAt(i));            //save word as ArrayList of individual chars
                    wordBlank.add("_");                       //each letter will be '_'
                    System.out.println(wordArray.toString()); //TODO: delete after debug
                    System.out.println(wordBlank.toString()); //TODO: delete after debug
                }
                for(int j = 0; j < wordArray.size(); j++) {   //write '_' for each letter in phrase
                    displayLabel.setText(wordBlank.toString()
                            .replace("[", "")
                            .replace("]", "")
                            .replace(",", ""));
                }
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

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
        System.out.println("Submit clicked: " + guessTextField.getText() + " guessed"); //FOR DEBUG
        guessTextField.clear();
    }

}