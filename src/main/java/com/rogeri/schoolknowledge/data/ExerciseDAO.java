package com.rogeri.schoolknowledge.data;

import com.rogeri.schoolknowledge.controler.Main;
import com.rogeri.schoolknowledge.model.Exercise;

import java.util.ArrayList;

/**
 * Created by rogeri on 18/03/16.
 */
public class ExerciseDAO {
    private ArrayList<ArrayList<Exercise>> exercises;

    public ExerciseDAO() {
        Exercise exercise = new Exercise(0, 0, 0, Main.questionDAO.getQuestionsByGameExercise(0, 0));
        ArrayList<Exercise> game0 = new ArrayList<>();
        game0.add(exercise);
        exercises.add(game0);
    }

    public ArrayList<Exercise> getExercisesByGame(int id) {
        return exercises.get(id);
    }
    /*
    TODO :
    - getExercise(int game, int exercise)
    - addExercise(Exercise e)
    */
}
