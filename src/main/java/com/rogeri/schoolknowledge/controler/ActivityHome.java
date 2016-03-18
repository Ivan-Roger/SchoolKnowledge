package com.rogeri.schoolknowledge.controler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.rogeri.schoolknowledge.R;
import com.rogeri.schoolknowledge.model.User;

public class ActivityHome extends AppCompatActivity {
    private final int LOGIN_REQUEST=40;
    private static int playerID;
    private static boolean anonymousMode;
    private static boolean loggedIn = false;

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

        if (anonymousMode) {
            Toast.makeText(this, "Jeu en Invité", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Jeu avec "+Main.userDAO.getJoueur(playerID).getName(), Toast.LENGTH_SHORT).show();
        }
    }
}
