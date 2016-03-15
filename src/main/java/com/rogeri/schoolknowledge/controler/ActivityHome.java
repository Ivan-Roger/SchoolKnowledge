package com.rogeri.schoolknowledge.controler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.rogeri.schoolknowledge.R;
import com.rogeri.schoolknowledge.model.User;

public class ActivityHome extends AppCompatActivity {
    private int playerID;
    private boolean anonymousMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        anonymousMode = getIntent().getBooleanExtra(ActivityLogin.EXTRA_ANONYMOUS_MODE,true);
        playerID = getIntent().getIntExtra(ActivityLogin.EXTRA_USER,-1);

        if (anonymousMode) {
            Toast.makeText(this, "Jeu en Invité", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Jeu avec "+Main.userDAO.getJoueur(playerID).getName(), Toast.LENGTH_SHORT).show();
        }
    }
}
