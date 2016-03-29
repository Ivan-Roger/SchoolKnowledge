package com.rogeri.schoolknowledge.controler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rogeri.schoolknowledge.R;
import com.rogeri.schoolknowledge.data.DAOQuestionQCM;
import com.rogeri.schoolknowledge.model.Question;
import com.rogeri.schoolknowledge.model.QuestionQCM;

/**
 * Created by rogeri on 11/03/16.
 */
public class ActivityQCM extends AppCompatActivity {
    public static final String EXTRA_QUESTION_ID = "questionID";
    private QuestionQCM question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_qcm);

        DAOQuestionQCM dao = new DAOQuestionQCM(this);
        question = dao.retrieveByID(getIntent().getStringExtra(EXTRA_QUESTION_ID));

        // On affiche le texte de la question //
        TextView qView = (TextView) findViewById(R.id.question_qcm_question_text);
        qView.setText(question.getQuestion()); // Appeller le getter de l'objet modèle : QuestionQCM

        //*
        // On affiche les réponses //
        LinearLayout aView = (LinearLayout) findViewById(R.id.question_qcm_answers_frame);
        for (String ans: question.getReponses()) {
            TextView ansV = new TextView(this);
            ansV.setText(ans);
            RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams)ansV.getLayoutParams();
            //p.addRule(RelativeLayout.LayoutParams.MATCH_PARENT);
            aView.addView(ansV);
        }

    }
}
/*
 * TODO:
 * - addView pour chaque reponse
 * - retourner score (0:defaite, etc...)
 */