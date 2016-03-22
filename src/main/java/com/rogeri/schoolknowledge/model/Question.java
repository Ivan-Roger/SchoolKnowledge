package com.rogeri.schoolknowledge.model;

/**
 * Created by rogeri on 08/03/16.
 */
public abstract class Question {
    private final String id;
    private final int score;

    public Question(int gameID, int exerciseID, int id, int score) {
        this.id = gameID+":"+exerciseID+":"+id;
        this.score = score;
    }

    public String getID() { return id; }
    public int getExercise() { return 0; }
    public int getGame() { return 0; }
    public int getScore() {
        return score;
    }
}
