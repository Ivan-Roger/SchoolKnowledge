package com.rogeri.schoolknowledge.controler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.rogeri.schoolknowledge.R;
import com.rogeri.schoolknowledge.model.User;

public class ActivityHome extends AppCompatActivity {
    private boolean anonymousMode;
    private User joueur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        anonymousMode = getIntent().getBooleanExtra(ActivityLogin.EXTRA_ANONYMOUS_MODE,true);

        if (anonymousMode) {
            Toast.makeText(this, "Jeu en Invité", Toast.LENGTH_SHORT).show();
        } else {
            joueur = (User)getIntent().getSerializableExtra(ActivityLogin.EXTRA_USER);
            Toast.makeText(this, "Jeu avec "+joueur.getName(), Toast.LENGTH_SHORT).show();
        }
    }
}
