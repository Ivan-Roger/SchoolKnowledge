package com.rogeri.schoolknowledge.model;

import com.rogeri.schoolknowledge.model.Exercise;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by rogeri on 08/03/16.
 */
public class Game implements Serializable {
    private final String id;
    private final String name;
    private final ArrayList<Exercise> exercises;

    public Game(String id, String name, ArrayList<Exercise> exercises) {
        this.id = id;
        this.name= name;
        this.exercises = exercises;
    }

    public String getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Exercise getExercise(int level) {
        return exercises.get(level);
    }

    public int getExerciseCount() {
        return exercises.size();
    }
}
