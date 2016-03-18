package com.rogeri.schoolknowledge.controler;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rogeri.schoolknowledge.R;
import com.rogeri.schoolknowledge.model.User;
import com.rogeri.schoolknowledge.view.UserViewAdapter;

import java.util.ArrayList;

public class ActivityLogin extends AppCompatActivity {
    private final int SIGNUP_REQUEST = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        RelativeLayout l = (RelativeLayout) findViewById(R.id.login_user_anonymous);
        l.setClickable(true);
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAnonymousLogin(v);
            }
        });

        updateLoginList();
    }

    private void updateLoginList() {
        ListView list = (ListView) findViewById(R.id.login_user_list);
        final UserViewAdapter adapter = new UserViewAdapter(this,R.layout.layout_user_template,Main.userDAO.getJoueurs());
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onUserLogin(adapter.getItem(position));
            }
        });
    }

    public void onUserNew(View v) {
        Intent intent = new Intent(this, ActivityNewUser.class);
        startActivityForResult(intent, SIGNUP_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==SIGNUP_REQUEST && resultCode==Activity.RESULT_OK) {
            super.finish();
        }
    }

    public void onUserLogin(User u) {
        ActivityHome.setLogin(u.getID());
        super.finish();
    }

    public void onAnonymousLogin(View v) {
        ActivityHome.setLogin();
        super.finish();
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Please login !", Toast.LENGTH_SHORT).show();
    }
}
