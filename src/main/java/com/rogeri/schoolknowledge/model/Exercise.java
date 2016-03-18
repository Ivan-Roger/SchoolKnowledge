package com.rogeri.schoolknowledge.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rogeri on 08/03/16.
 */
public class Exercise {
    private final int id;
    private final int gameID;
    private final int level;
    private final ArrayList<Question> questions;

    public Exercise(int id, int gameID, int level, ArrayList<Question> questions) {
        this.id = id;
        this.gameID = gameID;
        this.level = level;
        this.questions = questions;
    }

    public int getLevel() { return level; }

    public int getLength() {
        return 0;
    }

    public Question getQuestion(int id) {
        return questions.get(id);
    }
}
