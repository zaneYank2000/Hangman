package com.example.aoop_final_project;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;


public class GameController
{
    //Sound files
    String winSound = "src/main/resources/com/example/aoop_final_project/visuals/happy_wheels_win.mp3";
    String loseSound = "src/main/resources/com/example/aoop_final_project/visuals/Wilhelm-Scream.mp3";
    //Arraylists to store word information
    ArrayList wordArray = new ArrayList(); //used to save word as char array
    ArrayList wordBlank = new ArrayList(); //used to save word as char array with each char as '_'
    // Remaining lives
    private int lives = 7;
    // Indicator to keep playing game
    private boolean keepPlaying = true;
    // Loop thing to check for guesses
    Timeline timeline;

    //make socket
    InetAddress addr = InetAddress.getByName("localhost");
    Socket socket = new Socket(addr, 5001);
    ObjectOutputStream oos;
    ObjectInputStream ois;

    @FXML
    private Line armLeft;
    @FXML
    private Line armRight;
    @FXML
    private Line body;
    @FXML
    private Circle head;
    @FXML
    private Line legLeft;
    @FXML
    private Line legRight;

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
    private Label lettersUsedLabel;
    @FXML
    private Button update;
    @FXML
    private Label usernameLabel;


    public GameController() throws IOException {
        // Create a new output stream
        oos = new ObjectOutputStream(socket.getOutputStream());
        System.out.println("OUTPUT STREAM");

        if (!keepPlaying) timeline.stop();
        timeline = new Timeline(new KeyFrame(Duration.millis(3000), e -> {
            try {
                updateButtonClicked();
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);

        timeline.play();
    }

    public void initialize() throws IOException, ClassNotFoundException {
        //change name label
        Scanner scan = new Scanner(new File("member.txt"));
        String name = scan.nextLine();
        usernameLabel.setText("Welcome, " + name.substring(0, name.indexOf(",")));

        //read phrase from 'word.txt' and save it to an ArrayList
        try {
            //print gameID
            gameID.setText("Game ID: " + addr);

            //read from file (used only to get word from popup page to this page)
            File myObj = new File("word.txt");
            Scanner myReader = new Scanner(myObj);

            //write word to socket
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
        Parent root = FXMLLoader.load(getClass().getResource("newerMenu.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void submitButtonClicked(ActionEvent event) throws IOException {

    }
    
    private void checkLives(int lives) {
        switch (lives) {
            case 6 -> head.setVisible(true);
            case 5 -> body.setVisible(true);
            case 4 -> armLeft.setVisible(true);
            case 3 -> armRight.setVisible(true);
            case 2 -> legLeft.setVisible(true);
            case 1 -> {
                legRight.setVisible(true);
                youWinImage1.setVisible(true);
                keepPlaying = false;
            }
        }
    }

    @FXML
    public void updateButtonClicked() throws IOException, ClassNotFoundException {
        if (ois == null) {
                ois = new ObjectInputStream(socket.getInputStream());
        }

        String guess = (String) ois.readObject();
        if(guess.length() > 1) {
           guess = (String) ois.readObject();
        }
        //guess = (String) ois.readObject();

        System.out.println(guess + " GIVEN FROM SERVER");

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
            System.out.println("You lost, other player guessed word");
            gameOverImage.setVisible(true);
            keepPlaying = false;
        }

        guessTextField.clear();
    }

    // Indicate a loss to the user
    private void lose() {
        gameOverImage.setVisible(true);
        Media sound = new Media(loseSound);
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    // Indicate a win to the loser
    private void win() {
        youWinImage1.setVisible(true);
        Media sound = new Media(winSound);
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

}