package com.rogeri.schoolknowledge.controler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.rogeri.schoolknowledge.data.DAOExercise;
import com.rogeri.schoolknowledge.data.DAOQuestion;
import com.rogeri.schoolknowledge.model.Exercise;
import com.rogeri.schoolknowledge.model.Question;

import java.util.List;

/**
 * Created by rogeri on 01/04/16.
 */
public class ActivityExercise extends AppCompatActivity {
    public static final String EXTRA_EXERCISE_ID = "exerciseID";
    private Exercise exo;
    private List<Question> questions;
    private int curQuestionIndex=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String level = getIntent().getStringExtra(EXTRA_EXERCISE_ID);
        Log.d("LevelSelection-DEBUG", "Launching level " + level);
        try {
            DAOExercise daoExo = new DAOExercise(this);
            exo = daoExo.retrieveByID(level);

            DAOQuestion dao = new DAOQuestion(this);
            questions = dao.listByExerciseID(exo.getID());

            if (questions.size()>0) {
                startQuestion();
            } else
                throw new Exception("LevelSelection: No question for level "+exo.getID());
        } catch (Exception e) {
            Log.e("LevelSelection-ERROR",e.getMessage());
            e.printStackTrace();
        }
    }

    public void startQuestion() throws Exception {
        if (questions.size()>0 && curQuestionIndex<questions.size()-1) {
            curQuestionIndex++;
            Question q = questions.get(curQuestionIndex);
            if (q!=null) {
                Intent intent;
                // Ajouter les autres types de questions dans ce if
                if (q.type() == Question.TYPE_QCM)
                    intent = new Intent(this, ActivityQuestionQCM.class);
                else
                    throw new Exception("LevelSelection: Undefined question type");

                intent.putExtra(ActivityQuestionQCM.EXTRA_QUESTION_ID, q.getID());
                startActivity(intent);
            }
        }
    }
}
