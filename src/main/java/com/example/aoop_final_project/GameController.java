package com.example.aoop_final_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

public class GameController {

    @FXML
    private Text aText;

    @FXML
    private Line armLeft;

    @FXML
    private Line armRight;

    @FXML
    private Text bTExt;

    @FXML
    private HBox blankHBox;

    @FXML
    private Line body;

    @FXML
    private Text cText;

    @FXML
    private Text dText;

    @FXML
    private Text eText;

    @FXML
    private Text fText;

    @FXML
    private Text gText;

    @FXML
    private Label gameID;

    @FXML
    private TextField guessTextField;

    @FXML
    private Text hText;

    @FXML
    private Circle head;

    @FXML
    private Text iText;

    @FXML
    private Text jText;

    @FXML
    private Text kText;

    @FXML
    private Text lText;

    @FXML
    private Line legLeft;

    @FXML
    private Line legRight;

    @FXML
    private Text mText;

    @FXML
    private Text nText;

    @FXML
    private Text oText;

    @FXML
    private Text pText;

    @FXML
    private Text qText;

    @FXML
    private Text rText;

    @FXML
    private Text sText;

    @FXML
    private Text stringWord;

    @FXML
    private Label stringLabel;

    @FXML
    private Text tText;

    @FXML
    private Text uText;

    @FXML
    private Label user1Score;

    @FXML
    private Label user2Score;

    @FXML
    private Label username1;

    @FXML
    private Label username2;

    @FXML
    private Text vText;

    @FXML
    private Text wText;

    @FXML
    private Text xText;

    @FXML
    private Text yText;

    @FXML
    private Text zText;

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
        //Text t = new Text("_");
        //t.setFont(Font.font(24));

        //for(int i = 0; i < 3; i++) {
        //    blankHBox.getChildren().add(t);
        //}

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