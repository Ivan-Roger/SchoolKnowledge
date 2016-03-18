package com.rogeri.schoolknowledge.model;

/**
 * Created by rogeri on 08/03/16.
 */
public abstract class Question {
    private final int id;
    private final int exerciseID;
    private final int gameID;
    private final int score;

    public Question(int gameID, int exerciseID, int id, int score) {
        this.gameID = gameID;
        this.exerciseID = exerciseID;
        this.id = id;
        this.score = score;
    }

    public int getID() {
        return id;
    }
    public int getScore() {
        return score;
    }
}
