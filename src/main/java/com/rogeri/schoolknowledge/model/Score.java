package com.rogeri.schoolknowledge.model;

/**
 * Created by rogeri on 02/04/16.
 */
public class Score {
    private final String levelID;
    private final int userID;
    private int score;

    public Score(String levelID, int userID, int score) {
        this.levelID=levelID;
        this.userID=userID;
        this.score = score;
    }

    public Score(int gameID, int levelID, int userID, int score) {
        this.levelID=gameID+":"+levelID;
        this.userID=userID;
        this.score = score;
    }

    public int getScore() { return score; }

    public void setScore(int score) { this.score=score; }

    public String getExerciseID() { return levelID; }

    public int getUserID() { return userID; }
}
