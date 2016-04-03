package com.rogeri.schoolknowledge.controler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.Activity;
import android.util.Log;

import com.rogeri.schoolknowledge.R;
import com.rogeri.schoolknowledge.SchoolKnowledge;
import com.rogeri.schoolknowledge.data.DAOQuestionQCM;
import com.rogeri.schoolknowledge.model.Question;
import com.rogeri.schoolknowledge.model.QuestionQCM;

import java.util.ArrayList;

/**
* Created by rogeri on 11/03/16.
*/
public class ActivityQuestionQCM extends AppCompatActivity {
    private static final String LOG_TAG=SchoolKnowledge.LOG_TAG+"-QuestionQCM";
    private QuestionQCM question;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_qcm);

        DAOQuestionQCM dao = new DAOQuestionQCM(this);
        try {
            question = dao.retrieveByID(getIntent().getStringExtra(ActivityExercise.EXTRA_QUESTION_ID));
        } catch (Exception e){
            e.printStackTrace();
        }

        // On affiche le texte de la question //
        TextView qView = (TextView) findViewById(R.id.question_qcm_question_text);
        qView.setText(question.getQuestion()); // Appeller le getter de l'objet modèle : QuestionQCM

        // On affiche les réponses //
        LinearLayout aView = (LinearLayout) findViewById(R.id.question_qcm_answers_frame);
        for (String ans: question.getReponses()) {
            CheckBox ansV = new CheckBox(this);
            ansV.setText(ans);
            RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams)ansV.getLayoutParams();
            //p.addRule(RelativeLayout.LayoutParams.MATCH_PARENT);
            aView.addView(ansV);
        }

        if (question.isShowCount()) {
            TextView count = (TextView) findViewById(R.id.question_qcm_answers_count_info);
            count.setText(question.getAnswersCount()+" réponses");
        }
    }

    public void onNext(View v) {
        int score = 0;
        LinearLayout aView = (LinearLayout) findViewById(R.id.question_qcm_answers_frame);
        ArrayList<Boolean> ans = new ArrayList<>();
        String ansStr="";
        for (int i=0; i<aView.getChildCount(); i++) {
            if (((CheckBox) aView.getChildAt(i)).isChecked()) {
                ansStr+="\""+((CheckBox) aView.getChildAt(i)).getText()+"\", ";
            }
            ans.add(((CheckBox) aView.getChildAt(i)).isChecked());
        }
        if (question.checkAnswers(ans))
        score=question.getScore();

        Log.d(LOG_TAG,"Ending activity, Next question.");
        Intent _result = new Intent();
        _result.putExtra(ActivityExercise.EXTRA_RESULT, score);
        _result.putExtra(ActivityExercise.EXTRA_REPONSES, ansStr.substring(0,ansStr.length()-2));
        setResult(Activity.RESULT_OK, _result);
        finish();
    }
}
