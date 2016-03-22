package com.rogeri.schoolknowledge.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by rogeri on 08/03/16.
 */
public class Exercise {
    private final String id;
    private final int level;
    private final Collection<Question> questions;

    public Exercise(int id, int gameID, int level, Collection<Question> questions) {
        this.id = gameID+":"+id;
        this.level = level;
        this.questions = questions;
    }

    public String getID() { return id; }

    public int getLevel() { return level; }

    public int getLength() {
        return 0;
    }

    public Question getQuestion(int id) {
        return ((Question[])questions.toArray())[id];
    }
}
