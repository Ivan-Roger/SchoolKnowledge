package com.rogeri.schoolknowledge.controler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.rogeri.schoolknowledge.R;

public class ActivityLevelSelection extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_selection);
        Toast.makeText(this, "dwx:"+getIntent().getIntExtra(ActivityHome.EXTRA_GAME_ID,0), Toast.LENGTH_SHORT).show();
    }
}
