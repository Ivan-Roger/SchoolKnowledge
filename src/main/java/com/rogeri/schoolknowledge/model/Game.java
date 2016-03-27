package com.rogeri.schoolknowledge.model;

import com.rogeri.schoolknowledge.model.Exercise;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by rogeri on 08/03/16.
 */
public class Game {
    private final int id;
    private final String name;
    private final int picID;
    private final Collection<Exercise> exercises;
    private final String info;


    public Game(int id, String name, int picID, Collection<Exercise> exercises, String info) {
        this.id = id;
        this.name = name;
        this.picID = picID;
        this.exercises = exercises;
        this.info = info;
    }

    public int getID() { return id; }

    public String getName() { return name; }

    public String getInfo() { return info; }

    public int getPic() { return picID; }

    public Exercise getExercise(int level) {
        return (Exercise)exercises.toArray()[level];
    }

    public int getExerciseCount() {
        return exercises.size();
    }
}
