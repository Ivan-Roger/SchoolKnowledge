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

    abstract public int type();
    public String getID() { return id; }
    public String nextId() {
        String[] ids = id.split(":");
        int nID = Integer.parseInt(ids[2])+1;

        return ids[0]+":"+ids[1]+":"+nID;
    }
    public int getScore() {
        return score;
    }
}
