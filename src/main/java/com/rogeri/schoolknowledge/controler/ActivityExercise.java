package com.rogeri.schoolknowledge.controler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.ListView;
import android.app.Activity;
import android.util.Pair;
import android.util.Log;
import android.view.View;

import com.rogeri.schoolknowledge.R;
import com.rogeri.schoolknowledge.SchoolKnowledge;
import com.rogeri.schoolknowledge.data.DAOScore;
import com.rogeri.schoolknowledge.data.DAOExercise;
import com.rogeri.schoolknowledge.data.DAOQuestion;
import com.rogeri.schoolknowledge.model.Exercise;
import com.rogeri.schoolknowledge.model.Score;
import com.rogeri.schoolknowledge.model.User;
import com.rogeri.schoolknowledge.model.Question;
import com.rogeri.schoolknowledge.view.ResultViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
* Created by rogeri on 01/04/16.
*/
public class ActivityExercise extends AppCompatActivity {
  private static final String LOG_TAG=SchoolKnowledge.LOG_TAG+"-Exercise";
  public static final String EXTRA_QUESTION_ID = "questionID";
  public static final String EXTRA_RESULT = "resultat";
  public static final String EXTRA_EXERCISE_ID = "exerciseID";
  private static final String SAVE_CURRENT_QUESTION = "curQuestionIndex";
  private static final String SAVE_LEVEL_ID = "levelID";
  private static final String SAVE_SCORE = "scores";
  private static final int REQUEST_QUESTION = 60;
  private Exercise exo;
  private List<Question> questions;
  private ArrayList<Integer> score=new ArrayList<>();
  private int curQuestionIndex=-1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    String level="";
    if (savedInstanceState!=null) {
      curQuestionIndex = savedInstanceState.getInt(SAVE_CURRENT_QUESTION,-1);
      level = savedInstanceState.getString(SAVE_LEVEL_ID);
      score = (ArrayList<Integer>) savedInstanceState.getSerializable(SAVE_SCORE);
      Log.d(LOG_TAG,"Restauration des valeurs. (level="+level+", qI="+curQuestionIndex+")");
    } else {
      level = getIntent().getStringExtra(EXTRA_EXERCISE_ID);
      Log.d(LOG_TAG, "Launching level "+level+".");
    }
    try {
      DAOExercise daoExo = new DAOExercise(this);
      exo = daoExo.retrieveByID(level);

      DAOQuestion dao = new DAOQuestion(this);
      questions = dao.listByExerciseID(exo.getID());

      if (questions.size()>0) {
        startQuestion();
      } else
        throw new Exception(LOG_TAG+": No question for level ["+exo.getID()+"]("+exo.getName()+").");
    } catch (Exception e) {
      Log.e(LOG_TAG,e.getMessage());
      e.printStackTrace();
    }
  }

  protected void onSaveInstanceState(Bundle outState) {
    Log.d(LOG_TAG,"Saving instance state.");
    outState.putInt(SAVE_CURRENT_QUESTION,curQuestionIndex);
    outState.putString(SAVE_LEVEL_ID,exo.getID());
    outState.putSerializable(SAVE_SCORE,score);
    super.onSaveInstanceState(outState);
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
          throw new Exception(LOG_TAG+": Undefined question type");

        Log.d(LOG_TAG,"Launching question ["+q.getID()+"].");
        intent.putExtra(EXTRA_QUESTION_ID, q.getID());
        startActivityForResult(intent, REQUEST_QUESTION);
      }
    } else {
      displayResults();
    }
  }

  private void displayResults() {
    int scoreTot = 0;
    ArrayList<Pair<String,String>> resultats = new ArrayList<>();
    int i=1;
    for(int s: score) {
      scoreTot+=s;
      Pair<String,String> p = new Pair<>("Question "+i+":",(s>0?"Correct":"Faux"));
      resultats.add(p);
      i++;
    }
    Log.d(LOG_TAG,"Questions finished. Displaying results ("+scoreTot+"pts).");
    setContentView(R.layout.activity_exercise_result);
    TextView info = (TextView) findViewById(R.id.exercise_result_info);
    info.setText("Score: "+scoreTot+"pts.");
    SchoolKnowledge app = (SchoolKnowledge)getApplication();
    if (!app.isAnonymous()) {
      User p = app.getPlayer();
      DAOScore dao = new DAOScore(this);
      dao.updateOrInsert(new Score(exo.getID(),p.getID(),scoreTot));
    }

    ListView list = (ListView) findViewById(R.id.exercise_result_list);
    list.setAdapter(new ResultViewAdapter(this, R.layout.template_result_list,resultats));
  }

  private void displayAbort() {
    Log.d(LOG_TAG,"Aborting exercise.");
    setContentView(R.layout.activity_exercise_abort);
  }

  private void registerScore(int res) {
    Log.d(LOG_TAG,"Received activity result => "+res+"pts.");
    score.add(res);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode==REQUEST_QUESTION) {
      if (resultCode==Activity.RESULT_OK) {
        registerScore(data.getIntExtra(EXTRA_RESULT,-1));
        try {
          startQuestion();
        } catch (Exception e) {
          Log.e(LOG_TAG,e.getMessage());
          e.printStackTrace();
        }
      } else {
        displayAbort();
      }
    }
    super.onActivityResult(resultCode, requestCode, data);
  }

  public void onReplay(View v) {
    super.finish();
  }

  public void onQuit(View v) {
    Intent intent = new Intent(this, ActivityHome.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intent);
  }

  @Override
  public void onBackPressed() {
      onQuit(null);
  }
}
