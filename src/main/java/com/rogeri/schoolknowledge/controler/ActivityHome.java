package com.rogeri.schoolknowledge.controler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.rogeri.schoolknowledge.R;
import com.rogeri.schoolknowledge.model.Game;
import com.rogeri.schoolknowledge.model.User;
import com.rogeri.schoolknowledge.view.GameViewAdapter;

public class ActivityHome extends AppCompatActivity {
    private final int LOGIN_REQUEST=40;
    public static final String EXTRA_GAME_ID = "gameID";
    private static int playerID;
    private static boolean anonymousMode;
    private static boolean loggedIn = false;

    public static final int[] GAME_PICTURES = {R.mipmap.app_logo};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!loggedIn)
            promptLogin();
        else
            initialize();
    }

    private void promptLogin() {
        if (loggedIn) return;
        Intent connexion = new Intent(this, ActivityLogin.class);
        startActivityForResult(connexion, LOGIN_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==LOGIN_REQUEST) {
            if (loggedIn) {
                initialize();
            } else {
                promptLogin();
                Toast.makeText(this, "An error occured, please try again !", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void setLogin() {
        loggedIn = true;
        anonymousMode = true;
    }

    public static void setLogin(int id) {
        loggedIn = true;
        anonymousMode = false;
        playerID = id;
    }

    private void initialize() {
        setContentView(R.layout.activity_home);
        ListView gameList = (ListView) findViewById(R.id.listView);
        final GameViewAdapter adapter = new GameViewAdapter(this,R.layout.layout_user_template,Main.gameDAO.getGames());
        gameList.setAdapter(adapter);
        gameList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                playGame(adapter.getItem(position));
            }
        });

        if (anonymousMode) {
            Toast.makeText(this, "Jeu en Invité", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Jeu avec "+Main.userDAO.getJoueur(playerID).getName(), Toast.LENGTH_SHORT).show();
        }
    }

    private void playGame(Game g) {
        Intent intent = new Intent(this, ActivityLevelSelection.class);
        intent.putExtra(EXTRA_GAME_ID,g.getID());
        startActivity(intent);
    }
}
