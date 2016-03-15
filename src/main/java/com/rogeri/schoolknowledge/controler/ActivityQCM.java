package com.rogeri.schoolknowledge.controler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rogeri.schoolknowledge.R;

/**
 * Created by rogeri on 11/03/16.
 */
public class ActivityQCM extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_qcm);

        /* On affiche le texte de la question */
        TextView qView = (TextView) findViewById(R.id.question_qcm_question_text);
        qView.setText(null); // Appeller le getter de l'objet modèle : QuestionQCM

        /* On affiche les réponses */
        LinearLayout aView = (LinearLayout) findViewById(R.id.question_qcm_answers_frame);
        aView.addView(null);
    }
}
