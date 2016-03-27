package com.rogeri.schoolknowledge.model;

import java.util.HashMap;
import java.util.Set;

/**
 * Created by rogeri on 08/03/16.
 */
public class User {
    private int id;
    private String name;
    private int pic;
    private HashMap<String,Integer> scores;
    private static int lastID=0;

    public User(int id, String name,int pic) {
        lastID=Math.max(lastID,id)+1;
        this.id=id;
        this.name = name;
        this.pic = pic;
        scores = new HashMap<String,Integer>();
    }

    public static int getNextID() {
        return lastID;
    }

    public int getID() { return id; }

    public String getName() {
        return this.name;
    }

    public int getPic() { return pic; }

    public void setScores(HashMap<String,Integer> scores) {
        this.scores = scores;
    }

    public int getTotalScore() {
        int res=0;
        for (String game : scores.keySet()) {
            res+=scores.get(game);
        }
        return res;
    }

    public int getScore(String game) {
        return scores.get(game);
    }

    public Set<String> getScores() {
        return scores.keySet();
    }

    public void newScore(String game, int value) {
        scores.put(game, value);
    }

    public void addToScore(String game, int value) {
        scores.put(game, (scores.get(game) + value) );
    }
}
