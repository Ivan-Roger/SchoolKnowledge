package com.rogeri.schoolknowledge.data;

import com.rogeri.schoolknowledge.controler.Main;
import com.rogeri.schoolknowledge.model.Exercise;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by rogeri on 18/03/16.
 */
public class ExerciseDAO {
    private HashMap<String,Exercise> exercises = new HashMap<>();

    public ExerciseDAO() {
        Exercise exercise = new Exercise(0, 0, 0, Main.questionDAO.getQuestionsByGameExercise(0, 0));
        exercises.put(exercise.getID(), exercise);
    }

    public Collection<Exercise> getExercisesByGame(int id) {
        return exercises.values();
    }
    /*
    TODO :
    - getExercise(int game, int exercise)
    - addExercise(Exercise e)
    */
}
