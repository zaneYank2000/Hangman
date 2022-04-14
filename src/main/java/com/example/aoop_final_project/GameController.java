package com.example.aoop_final_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import javax.swing.*;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;


public class GameController
{
    // Instance variables
    ArrayList wordArray = new ArrayList(); //used to save word as char array
    ArrayList wordBlank = new ArrayList(); //used to save word as char array with each char as '_'
    static int lives = 7;
    boolean keepPlaying = true;
    String myName;
    String gameWord;
    String serverName, guess;
    boolean isHost;
    WordInformation wordInfo;
    ObjectOutputStream myOutputStream;
    ObjectInputStream objectInputStream;

    private static final int PORT = 5001;

    //FXML Objects
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
    private TextField guessTextField;
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

    public void prepareLabel() {
        // Hides the guess filed before ethe game starts
        //guessTextField.setVisible(false);

        //read phrase from 'word.txt' and save it to an ArrayList
        //try {
            //File myObj = new File("src/main/resources/com/example/aoop_final_project", "word.txt");
            //Scanner myReader = new Scanner(myObj);
            //while (myReader.hasNextLine()) {                      //while file still has word
                //String word = myReader.nextLine();                //extract word
                for (int i = 0; i < gameWord.length(); i++) {
                    wordArray.add(gameWord.charAt(i));                //save word as ArrayList of individual chars
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
            //}
            //myReader.close();
        //} catch (FileNotFoundException e) {
           // System.out.println("An error occurred.");
            //e.printStackTrace();
        //}


        //ex
        //wordlarray = (WOrdInfo) stream.readobject().getarray()
    }

    @FXML
    void exitButtonClicked(ActionEvent event)  {
        /*
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Menu");
        stage.setScene(scene);
        stage.show();
         */
        System.exit(1);
    }

    private void checkLives(int lives) {
        switch (lives) {
            case 6: head.setVisible(true); break;
            case 5: body.setVisible(true); break;
            case 4: armLeft.setVisible(true); break;
            case 3: armRight.setVisible(true); break;
            case 2: legLeft.setVisible(true); break;
            case 1: legRight.setVisible(true); break;
            case 0: gameOverImage.setVisible(true);
                    //decide what happens when this gets pushed to server
        }
    }


    @FXML
    private void startGame(ActionEvent event) throws IOException {
        myName = JOptionPane.showInputDialog("Enter a user name: ");
        serverName = JOptionPane.showInputDialog("Enter server name: ");
        try {
            if (isHost == false) {
                do {
                    gameWord = JOptionPane.showInputDialog("Enter a word/phrase: ");
                } while (gameWord.equals("".trim()));
            }
        } catch (NullPointerException e) {
            System.out.println("null pointer exception");
        }

        //Initialise the label with the word
        wordInfo = new WordInformation(gameWord);
        wordInfo.setHost(true);
        isHost = wordInfo.isHost();

        InetAddress addr = InetAddress.getByName(serverName);
        Socket socket = new Socket(addr, PORT);

        myOutputStream = new ObjectOutputStream(socket.getOutputStream());
        myOutputStream.flush();

        // Send client's name to server
        try {
            System.out.println("in game " + wordInfo.toString());
            myOutputStream.writeObject(wordInfo); //was name
            myOutputStream.flush();
        } catch (NullPointerException e) {
            System.out.println("null pointer sending wordinfo");
        } catch (NotSerializableException e) {
            System.out.println("not serializible sending wordinfo");
        }
        ObjectInputStream myInputStream = new ObjectInputStream(socket.getInputStream());

        username1.setText(myName);
        System.out.println("Welcome to the game, " + myName);
        // Use a thread to receive messages form server
        ClientConnection c = new ClientConnection(myInputStream);
        System.out.println("Created a new ClientConnection object...");
        c.start();

        //Login stuff
        /*
        if(user.trim().equals("") || pass.trim().equals("")) {
            // Error message
            JOptionPane.showMessageDialog(null, "Username or password are empty.");
            return;
        }

        username1.setText(user);
        guessTextField.setVisible(true);
        File usersFile = new File("src/main/resources/com/example/aoop_final_project/users.dat");

        if (usersFile.exists()) {
            try {
                FileInputStream fin = new FileInputStream(usersFile);
                ObjectInputStream ois = new ObjectInputStream(fin);

                //TODO: Make sure we can have more than 5 users
                for (int i = 0; i < 5; i++) {   //Note: only allows to store 5 different users
                    //Get a user from the users.dat file
                    User test = (User) ois.readObject();
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
         */
    }

    @FXML
    void aboutGame(ActionEvent e) {
        JOptionPane.showMessageDialog(null, "Just enter a guess till u win or lose");
    }

    @FXML
    void enterMessage(ActionEvent event) {
        //Get the guess from the textbox
        try {
            //if (!isHost) {
                //do {
                    String guess = guessTextField.getText().trim();
                    System.out.println("Guess: " + guess);
                    wordInfo.setGuess(guess);;
                //} while (guess.equals());
            //}
        } catch (NullPointerException e) {
            System.out.println("null pointer exception thrown. Why");
        }
        guessTextField.setText("");
        //System.out.println(wordInfo.getGuess());

        try {
            //Send message to server
            System.out.println("About to send a message...");
            myOutputStream.writeObject(wordInfo);
            myOutputStream.flush();
            System.out.println("Sent guess, '" + wordInfo.getGuess() + "', to server");
        }
        catch (IOException ioException)  {
            System.out.println("Problem sending guess to server.");
        }

        if(wordInfo.getGuess().equals("".trim())) {                                             //no letter guessed
            guessTextField.setPromptText("Please insert letter");
        } else if (wordInfo.getWordArray().contains(guess)) {                //letter(s) found in phrase

            for(int k = 0; k < wordInfo.getWordArray().size(); k++) {
                if(wordInfo.getWordArray().get(k).toString().equals(wordInfo.getGuess())) {           //if letter is in phrase, show it
                    wordInfo.getWordBlank().set(k, guess);
                    displayLabel.setText(wordInfo.getWordBlank().toString()
                            .replace("[", "")
                            .replace("]", "")
                            .replace(",", ""));
                }
            }

            //Send guess to the server
            try {
                myOutputStream.writeObject(wordInfo);
                myOutputStream.flush();
                System.out.println("Client has sent: " + wordInfo.toString());
            } catch (IOException e) {
                System.out.println("Problem sending message to server.");
            }

        } else {                                                          //letter not found in phrase
            lives--;
            checkLives(lives);
        }
        if(wordInfo.getWordArray() == wordInfo.getWordBlank()) {            //if full phrase is guessed, player wins
            System.out.println("GAME WON");
        }
        //Clear the textfield
        guessTextField.clear();
    }

    private class ClientConnection extends Thread  {

        ObjectInputStream inStr;

        public ClientConnection (ObjectInputStream oistr) {
            inStr = oistr;
        }

        // Receive messages from server and display them at client end
        public void run()
        {
            System.out.println("Entered ClientConnection run method...");
            while (true) {
                try {
                    WordInformation w = (WordInformation) inStr.readObject();
                    System.out.println("w " + wordInfo);
                    wordInfo.setGuess(w.getGuess()); //= w;
                    System.out.println("wordinfo " + wordInfo);
                    //displayLabel.setText(w.getWordBlank().toString()); // why does the label NOT change
                    JOptionPane.showMessageDialog(null, wordInfo.getWordBlank());
                    //send to the letter guesser

                }
                catch (ClassNotFoundException e)  {
                    System.out.println("Error receiving message....shutting down");
                }
                catch (IOException e) {
                    System.out.println("Problem reading");
                }
            }
        }
    }

}