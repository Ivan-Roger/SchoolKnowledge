package com.rogeri.schoolknowledge.controler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;

import com.rogeri.schoolknowledge.R;
import com.rogeri.schoolknowledge.SchoolKnowledge;
import com.rogeri.schoolknowledge.data.DAOGame;
import com.rogeri.schoolknowledge.data.DAOUser;
import com.rogeri.schoolknowledge.model.Game;
import com.rogeri.schoolknowledge.model.User;
import com.rogeri.schoolknowledge.view.GameViewAdapter;

public class ActivityHome extends AppCompatActivity {
    private static final String LOG_TAG=SchoolKnowledge.LOG_TAG+"-Home";
    private static final String SAVE_ANONYMOUS="anonymous";
    private static final String SAVE_PLAYER_ID="playerID";
    private static final int REQUEST_PLAY=10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SchoolKnowledge app = (SchoolKnowledge)getApplication();
        if (savedInstanceState!=null) {
            Log.d(LOG_TAG,"Restauration des valeurs.");
            if (!app.isLoggedIn()) {
                if (savedInstanceState.getInt(SAVE_ANONYMOUS,-1)==1) {
                    app.setAnonymous();
                } else {
                    DAOUser daoU = new DAOUser(this);
                    int idP = savedInstanceState.getInt(SAVE_PLAYER_ID,-1);
                    if (idP!=-1) {
                        User u = daoU.retrieveByID(idP);
                        app.setPlayer(u);
                    }
                }
            }
        }
        if (app.isLoggedIn()) {
            Log.d(LOG_TAG,"Displaying game selection");
            // Après connexion: Affichage normal
            setContentView(R.layout.activity_home);

            DAOGame gameDAO = new DAOGame(this);
            ListView gameList = (ListView) findViewById(R.id.home_games_list);
            final GameViewAdapter adapter = new GameViewAdapter(this,R.layout.template_user_list,gameDAO.selectAll());
            gameList.setAdapter(adapter);
            gameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    playGame(adapter.getItem(position));
                }
            });

            if (app.isAnonymous()==false) {
                User u = app.getPlayer();
                ImageView pic = (ImageView) findViewById(R.id.home_user_pic);
                pic.setImageResource(ActivityNewUser.USER_PICTURES[u.getPic()]);
                TextView name = (TextView) findViewById(R.id.home_user_name);
                name.setText(u.getName());
                TextView info = (TextView) findViewById(R.id.home_user_info);
                updatePlayerScore();
            }
        } else {
            Log.d(LOG_TAG,"   ---   STARTING APPLICATION   ---   ");
            // Premier lancement: Connexion
            Intent intent = new Intent(this, ActivityLogin.class);
            startActivity(intent);
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        Log.d(LOG_TAG,"Saving instance state.");
        SchoolKnowledge app = (SchoolKnowledge)getApplication();
        if (app.isLoggedIn()) {
            outState.putInt(SAVE_ANONYMOUS,(app.isAnonymous()?1:0));
            if (!app.isAnonymous())
            outState.putInt(SAVE_PLAYER_ID,app.getPlayer().getID());
        }
        super.onSaveInstanceState(outState);
    }

    private void updatePlayerScore() {
        SchoolKnowledge app = (SchoolKnowledge)getApplication();
        if (!app.isAnonymous()) {
            try {
                User u = app.getPlayer();
                TextView info = (TextView) findViewById(R.id.home_user_info);
                info.setText("Score: "+u.getTotalScore(this));
            } catch(Exception e) {
                Log.e(LOG_TAG,e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private void playGame(Game g) {
        Intent intent = new Intent(this, ActivityLevelSelection.class);
        intent.putExtra(ActivityLevelSelection.EXTRA_GAME_ID, g.getID());
        Log.d(LOG_TAG,"Starting game "+g.getName()+"["+g.getID()+"].");
        startActivityForResult(intent,REQUEST_PLAY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==REQUEST_PLAY) {
            updatePlayerScore();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void logout(View v) {
        Intent intent = new Intent(this, ActivityLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ((SchoolKnowledge)getApplication()).logout();
        startActivity(intent);
    }
}
