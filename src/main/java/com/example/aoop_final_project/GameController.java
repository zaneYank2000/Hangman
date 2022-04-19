package com.example.aoop_final_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class GameController {
    ArrayList wordArray = new ArrayList(); //used to save word as char array
    ArrayList wordBlank = new ArrayList(); //used to save word as char array with each char as '_'
    int lives = 7;
    boolean keepPlaying = true;

    //make socket
    InetAddress addr = InetAddress.getByName("localhost");
    Socket socket = new Socket(addr, 5001);

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
    private ImageView gameOverImage;

    @FXML
    private ImageView youWinImage1;

    @FXML
    private TextField guessTextField;

    @FXML
    private Circle head;

    @FXML
    private Line legLeft;

    @FXML
    private Line legRight;

    @FXML
    private Label lettersUsedLabel;

    @FXML
    private Label user1Score;

    @FXML
    private Label user2Score;

    @FXML
    private Label username1;

    @FXML
    private Label username2;

    public GameController() throws IOException {
    }

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


    public void initialize() throws IOException, ClassNotFoundException {

        //read phrase from 'word.txt' and save it to an ArrayList
        try {
            //read from file
            File myObj = new File("word.txt");
            Scanner myReader = new Scanner(myObj);

            //write word to socket
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            Scanner wordScan = new Scanner(myObj);
            String word = wordScan.nextLine();


            for (int i = 0; i < word.length(); i++) {
                wordArray.add(word.charAt(i));                //save word as ArrayList of individual chars
                wordBlank.add("_");                           //each letter will be '_'
            }

            for(int j = 0; j < wordArray.size(); j++) {       //write '_' for each letter in phrase
                if (wordArray.get(j).toString().equals(" ")) { //auto reveal space characters
                    wordBlank.set(j, " ");
                }
            }

            displayLabel.setText(wordBlank.toString()
                    .replace("[", "")
                    .replace("]", "")
                    .replace(",", ""));

            System.out.println("about to send word to server " + word);
            oos.writeObject(word);


            /*
            while (myReader.hasNextLine()) {                      //while file still has word
                String word = myReader.nextLine();                //extract word
                for (int i = 0; i < word.length(); i++) {
                    wordArray.add(word.charAt(i));                //save word as ArrayList of individual chars
                    wordBlank.add("_");                           //each letter will be '_'
                }
                System.out.println(wordArray.toString()); //TODO: delete after debug
                for(int j = 0; j < wordArray.size(); j++) {       //write '_' for each letter in phrase
                    if(wordArray.get(j).toString().equals(" ")) { //auto reveal space characters
                        wordBlank.set(j, " ");
                    }
                    displayLabel.setText(wordBlank.toString()
                            .replace("[", "")
                            .replace("]", "")
                            .replace(",", ""));
                    System.out.println(wordBlank.toString()); //TODO: delete after debug
                }
            }
             */



            

            //close scanners
            myReader.close();
            wordScan.close();
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
        String guess = guessTextField.getText().toUpperCase(Locale.ROOT);

        if(guess.isEmpty()) {                                             //no letter guessed
            guessTextField.setPromptText("Please insert letter");
        } else if (wordArray.toString().contains(guess)) {                //letter(s) found in phrase
            for(int k = 0; k < wordArray.size(); k++) {
                if(wordArray.get(k).toString().equals(guess)) {           //if letter is in phrase, show it
                    wordBlank.set(k, guess);
                    displayLabel.setText(wordBlank.toString()
                            .replace("[", "")
                            .replace("]", "")
                            .replace(",", ""));
                }
            }
        } else {                                                          //letter not found in phrase
            lives--;
            checkLives(lives);
        }

        if(wordArray.toString().equals(wordBlank.toString())) {            //if full phrase is guessed, player wins
            System.out.println("GAME WON");
            youWinImage1.setVisible(true);
        }

        guessTextField.clear();
    }
    
    private void checkLives(int lives) {
        switch (lives) {
            case 6: head.setVisible(true); break;
            case 5: body.setVisible(true); break;
            case 4: armLeft.setVisible(true); break;
            case 3: armRight.setVisible(true); break;
            case 2: legLeft.setVisible(true); break;
            case 1: legRight.setVisible(true); gameOverImage.setVisible(true);; break;
        }
    }


    /*
    private void lose() {
        gameOverImage.setVisible(true);
        Media sound = new Media(loseSound);
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }


    private void win() {
        youWinImage1.setVisible(true);
        Media sound = new Media(winSound);
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

     */
}