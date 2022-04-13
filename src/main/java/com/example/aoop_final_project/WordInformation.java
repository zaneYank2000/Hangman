package com.example.aoop_final_project;

import java.util.ArrayList;

public class WordInformation {

    // Instance variables
    ArrayList wordArray = new ArrayList(); //used to save word as char array
    ArrayList wordBlank = new ArrayList(); //used to save word as char array with each char as '_'
    String guess, word;
    private boolean isHost;

    public WordInformation(String word) {
        this.word = word;
        initialiseArrays();
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }

    public ArrayList getWordArray() {
        return wordArray;
    }

    public void setWordArray(ArrayList wordArray) {
        this.wordArray = wordArray;
    }

    public ArrayList getWordBlank() {
        return wordBlank;
    }

    public void setWordBlank(ArrayList wordBlank) {
        this.wordBlank = wordBlank;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    private boolean initialiseArrays() {
        //Create word array from word
        for(int i = 0; i < word.length(); i++) {
            wordArray.add(word.charAt(i));
            wordArray.add("_");
        }

        for(int j = 0; j < wordArray.size(); j++) {       //write '_' for each letter in phrase
            if (wordArray.get(j).toString().equals(" ")) { //auto reveal space characters
                wordBlank.set(j, " ");
            }
        }
        System.out.println(wordBlank.toString()); //TODO: delete after debug


        if(guess.equals("")) {
            return false;   //no letter guessed
        } else if (wordArray.toString().contains(guess)) {                //letter(s) found in phrase
            for(int k = 0; k < wordArray.size(); k++) {
                if(wordArray.get(k).toString().equals(guess)) {           //if letter is in phrase, show it
                    wordBlank.set(k, guess);
                }
            }

            return true;
        } else {                                                          //letter not found in phrase
            return false;
        }
    }

    @Override
    public String toString() {
        return "WordInformation{" +
                "wordArray=" + wordArray +
                ", wordBlank=" + wordBlank +
                ", guess='" + guess + '\'' +
                ", word='" + word + '\'' +
                ", isHost=" + isHost +
                '}';
    }
}
