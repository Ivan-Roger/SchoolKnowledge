package com.rogeri.schoolknowledge.controler;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.rogeri.schoolknowledge.R;
import com.rogeri.schoolknowledge.SchoolKnowledge;
import com.rogeri.schoolknowledge.data.DAOExercise;
import com.rogeri.schoolknowledge.data.DAOGame;
import com.rogeri.schoolknowledge.data.DAOScore;
import com.rogeri.schoolknowledge.model.Exercise;
import com.rogeri.schoolknowledge.model.Game;
import com.rogeri.schoolknowledge.model.Score;
import com.rogeri.schoolknowledge.view.ExerciseViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class ActivityLevelSelection extends AppCompatActivity {
    private static final String LOG_TAG=SchoolKnowledge.LOG_TAG+"-LevelSelection";
    public static final String EXTRA_GAME_ID = "gameID";
    private Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selection);

        DAOGame gameDAO = new DAOGame(this);
        game = gameDAO.retrieveByID(getIntent().getIntExtra(EXTRA_GAME_ID, -1));

        ImageView pic = (ImageView) findViewById(R.id.level_game_pic);
        pic.setImageResource(Game.GAME_PICTURES[game.getPic()]);

        TextView name = (TextView) findViewById(R.id.level_game_name);
        name.setText(game.getName());

        ListView selector = (ListView) findViewById(R.id.level_selection_selector);
        DAOExercise exerciseDAO = new DAOExercise(this);
        DAOScore scoreDAO = new DAOScore(this);

        SchoolKnowledge app = (SchoolKnowledge)getApplication();

        final List<Exercise> exos = exerciseDAO.listByGameID(game.getID());
        ArrayList<ContentValues> exosList = new ArrayList<>();
        for (Exercise e: exos) {
            ContentValues c = new ContentValues();
            c.put(ExerciseViewAdapter.COL_TITLE, e.getName());
            if (!app.isAnonymous()) {
                try {
                    Score s = scoreDAO.retrieveByID(e.getID(),app.getPlayer().getID());
                    if (s!=null) {
                        c.put(ExerciseViewAdapter.COL_INFO,"Votre dernier score : "+s.getScore()+"pts.");
                    } else {
                        c.put(ExerciseViewAdapter.COL_INFO,"Vous n'avez pas encore joué ce niveau.");
                    }
                } catch (Exception ex) {
                    Log.e(LOG_TAG, ex.getMessage());
                    ex.printStackTrace();
                }
            } else {
                c.put(ExerciseViewAdapter.COL_INFO,"Jouez sans enregistrer votre score.");
            }
            exosList.add(c);
        }
        final ExerciseViewAdapter adapter = new ExerciseViewAdapter(this, R.layout.template_exercise_list, exosList);
        selector.setAdapter(adapter);
        selector.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onLevelSelected(exos.get(position).getID());
            }
        });
    }

    private void onLevelSelected(String level) {
        Intent intent = new Intent(this, ActivityExercise.class);
        intent.putExtra(ActivityExercise.EXTRA_EXERCISE_ID,level);
        startActivity(intent);
    }
}
