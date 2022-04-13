package com.example.aoop_final_project;

public class User {

    private String user;
    private String pass;
    private int score;

    public User(String user, String pass) {
        this.user = user;
        this.pass = pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
