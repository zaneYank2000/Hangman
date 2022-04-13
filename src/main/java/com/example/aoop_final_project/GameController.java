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
        //while(gameWord != null || isHost) {
            gameWord = JOptionPane.showInputDialog("Enter a word/phrase: ");
        //}

        //Initialise the label with the word
        prepareLabel();
        wordInfo = new WordInformation(gameWord);
        wordInfo.setHost(true);
        isHost = wordInfo.isHost();

        InetAddress addr = InetAddress.getByName(serverName);
        Socket socket = new Socket(addr, PORT);

        myOutputStream = new ObjectOutputStream(socket.getOutputStream());
        myOutputStream.flush();
        // Send client's name to server
        myOutputStream.writeObject(gameWord); //was name
        myOutputStream.flush();
        ObjectInputStream myInputStream = new ObjectInputStream(socket.getInputStream());

        username1.setText(myName);
        System.out.println("Welcome to the game, " + myName);
        // Use a thread to receive messages form server
        ClientConnection c = new ClientConnection(myInputStream);
        System.out.println("Created a new ClientConnection object...");
        c.start();

        //Login shit
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
        JOptionPane.showMessageDialog(null, "Go fuck yourself bitch ass hoe");
    }

    @FXML
    void enterMessage(ActionEvent event) {
        String guess = guessTextField.getText().trim();
        guessTextField.setText("");
        System.out.println(guess);
        try {
            //Send message to server
            System.out.println("About to send a message...");
            myOutputStream.writeObject(guess);
            myOutputStream.flush();
            System.out.println("Sent guess, '" + guess + "', to server");
        }
        catch (IOException ioException)  {
            System.out.println("Problem sending message to server.");
        }

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
            while (true)
            {
                try {
                    String receivedMessage = (String) inStr.readObject(); //dedded
                    //System.out.println("wordlabel should be " + receivedMessage);
                    //displayLabel.setText((String) inStr.readObject()); //fucking retarted ass label
                    JOptionPane.showMessageDialog(null, receivedMessage);
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