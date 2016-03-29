package com.rogeri.schoolknowledge.model;

/**
 * Created by rogeri on 08/03/16.
 */
public abstract class Question {
    public final static int TYPE_QCM = 1;
    public final static int TYPE_SIMPLE = 2;
    private final String id;
    private final int score;

    public Question(String id, int score) {
        this.id = id;
        this.score = score;
    }

    public String getID() { return id; }
    public int getScore() {
        return score;
    }
}
