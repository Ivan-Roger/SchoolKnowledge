package com.rogeri.schoolknowledge.model;

import java.io.Serializable;

/**
 * Created by rogeri on 08/03/16.
 */
public abstract class Question implements Serializable {
    private final int id;
    private final int score;

    public Question(int id, int score) {
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
