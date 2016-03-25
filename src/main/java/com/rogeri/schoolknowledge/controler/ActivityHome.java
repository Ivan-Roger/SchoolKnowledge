package com.rogeri.schoolknowledge.controler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rogeri.schoolknowledge.R;
import com.rogeri.schoolknowledge.data.DAOGame;
import com.rogeri.schoolknowledge.data.DAOUser;
import com.rogeri.schoolknowledge.model.Game;
import com.rogeri.schoolknowledge.model.User;
import com.rogeri.schoolknowledge.view.GameViewAdapter;

public class ActivityHome extends AppCompatActivity {
    public static final String EXTRA_LOGIN_MODE = "loginMode";
    public static final int LOGIN_MODE_USER = 0;
    public static final int LOGIN_MODE_ANONYMOUS = 1;
    public static final String EXTRA_PLAYER_ID = "playerID";

    public static final int[] GAME_PICTURES = {R.mipmap.app_logo};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null) {
            // Restauration: On remet les valeurs
            // TODO

            // Temporairement on ferme l'appli
            super.finish();
        } else if (getIntent().getIntExtra(EXTRA_LOGIN_MODE,-1)!=-1) {
            // Après connexion: Affichage normal
            setContentView(R.layout.activity_home);

            int playerID = getIntent().getIntExtra(EXTRA_PLAYER_ID,-1);
            int loginMode = getIntent().getIntExtra(EXTRA_LOGIN_MODE,-1);

            DAOGame gameDAO = new DAOGame();
            ListView gameList = (ListView) findViewById(R.id.home_games_list);
            final GameViewAdapter adapter = new GameViewAdapter(this,R.layout.layout_user_template,gameDAO.getGames());
            gameList.setAdapter(adapter);
            gameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    playGame(adapter.getItem(position));
                }
            });

            if (loginMode==LOGIN_MODE_ANONYMOUS) {
                Toast.makeText(this, "Jeu en Invité", Toast.LENGTH_SHORT).show();
            } else if (loginMode==LOGIN_MODE_USER) {
                DAOUser userDAO = new DAOUser();
                User u = userDAO.getJoueur(playerID);
                ImageView pic = (ImageView) findViewById(R.id.home_user_pic);
                pic.setImageResource(ActivityNewUser.USER_PICTURES[u.getPic()]);
                TextView name = (TextView) findViewById(R.id.home_user_name);
                name.setText(u.getName());
                TextView info = (TextView) findViewById(R.id.home_user_info);
                info.setText("Score: "+u.getTotalScore());
                Toast.makeText(this, "Jeu avec "+playerID+": "+u.getName(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Premier lancement: Connexion
            Intent intent = new Intent(this, ActivityLogin.class);
            startActivity(intent);
        }
    }
/*
    @Override
    public void onBackPressed() {
        super.finish();
    }
*/
    private void playGame(Game g) {
        Intent intent = new Intent(this, ActivityLevelSelection.class);
        intent.putExtra(ActivityLevelSelection.EXTRA_GAME_ID, g.getID());
        startActivity(intent);
    }

    public void logout(View v) {
        Intent intent = new Intent(this, ActivityLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        super.finish();
        // TODO: Doesn't work !
    }
}
