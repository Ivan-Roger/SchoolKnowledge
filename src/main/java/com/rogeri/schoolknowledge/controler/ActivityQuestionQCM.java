package com.rogeri.schoolknowledge.controler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rogeri.schoolknowledge.R;
import com.rogeri.schoolknowledge.data.DAOQuestionQCM;
import com.rogeri.schoolknowledge.model.Question;
import com.rogeri.schoolknowledge.model.QuestionQCM;

import java.util.ArrayList;

/**
 * Created by rogeri on 11/03/16.
 */
public class ActivityQuestionQCM extends AppCompatActivity {
    public static final String EXTRA_QUESTION_ID = "questionID";
    public static final String EXTRA_SCORE_RESULT = "score";
    private QuestionQCM question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_qcm);

        DAOQuestionQCM dao = new DAOQuestionQCM(this);
        try {
            question = dao.retrieveByID(getIntent().getStringExtra(EXTRA_QUESTION_ID));
        } catch (Exception e){
            e.printStackTrace();
        }

        // On affiche le texte de la question //
        TextView qView = (TextView) findViewById(R.id.question_qcm_question_text);
        qView.setText(question.getQuestion()); // Appeller le getter de l'objet modèle : QuestionQCM

        //*
        // On affiche les réponses //
        LinearLayout aView = (LinearLayout) findViewById(R.id.question_qcm_answers_frame);
        for (String ans: question.getReponses()) {
            CheckBox ansV = new CheckBox(this);
            ansV.setText(ans);
            RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams)ansV.getLayoutParams();
            //p.addRule(RelativeLayout.LayoutParams.MATCH_PARENT);
            aView.addView(ansV);
        }

    }

    public void onNext(View v) {
        int score = 0;
        LinearLayout aView = (LinearLayout) findViewById(R.id.question_qcm_answers_frame);
        ArrayList<Boolean> ans = new ArrayList<>();
        for (int i=0; i<aView.getChildCount(); i++) {
            ans.add(((CheckBox) aView.getChildAt(i)).isChecked());
        }
        if (question.checkAnswers(ans))
            score = question.getScore();

        getIntent().putExtra(EXTRA_SCORE_RESULT, score);
        // TODO: End activity and return score to ActivityExercise
    }
}