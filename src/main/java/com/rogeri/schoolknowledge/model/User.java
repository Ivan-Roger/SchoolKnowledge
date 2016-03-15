package com.rogeri.schoolknowledge.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by rogeri on 08/03/16.
 */
public class User implements Serializable {
    private String name;
    private int pic;
    private HashMap<String,Integer> scores;

    public User(String name,int pic) {
        this.name = name;
        this.pic = pic;
        scores = new HashMap<String,Integer>();
    }

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
