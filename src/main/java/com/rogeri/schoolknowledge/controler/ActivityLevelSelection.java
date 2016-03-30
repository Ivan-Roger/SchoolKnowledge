package com.rogeri.schoolknowledge.controler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rogeri.schoolknowledge.R;
import com.rogeri.schoolknowledge.data.DAOExercise;
import com.rogeri.schoolknowledge.data.DAOGame;
import com.rogeri.schoolknowledge.data.DAOQuestion;
import com.rogeri.schoolknowledge.data.DAOQuestionQCM;
import com.rogeri.schoolknowledge.model.Exercise;
import com.rogeri.schoolknowledge.model.Game;
import com.rogeri.schoolknowledge.model.Question;
import com.rogeri.schoolknowledge.model.QuestionQCM;

import java.util.List;

public class ActivityLevelSelection extends AppCompatActivity {
    public static final String EXTRA_GAME_ID = "gameID";
    private Game game;
    private List<Exercise> exos;

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

        GridLayout grid = (GridLayout) findViewById(R.id.level_selection_grid);
        DAOExercise exerciseDAO = new DAOExercise(this);
        List<Exercise> exos = exerciseDAO.listByGameID(game.getID());
        int i=0;
        for (Exercise e: exos) {
            Button v = new Button(this);
            v.setText(e.getName());
            final String lvl = e.getID();
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onLevelSelected(lvl);
                }
            });
            grid.addView(v);
        }
    }

    public void onLevelSelected(String level) {
        Toast.makeText(this, "Level: "+level, Toast.LENGTH_SHORT).show();
        DAOQuestion questionDAO = new DAOQuestion(this);
        List<Question> q = questionDAO.listByExerciseID(e.getID() + ":0");
        Intent intent;
        if (q instanceof QuestionQCM)
            intent = new Intent(this, ActivityQCM.class);
        else return;
        // Ajouter les autres types de questions dans ce if
        intent.putExtra(ActivityQCM.EXTRA_QUESTION_ID,e.getID());
        startActivity(intent);
    }
}
