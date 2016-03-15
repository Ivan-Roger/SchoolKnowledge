package com.rogeri.schoolknowledge.controler;

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

import com.rogeri.schoolknowledge.R;
import com.rogeri.schoolknowledge.model.User;
import com.rogeri.schoolknowledge.view.UserViewAdapter;

import java.util.ArrayList;

public class ActivityLogin extends AppCompatActivity {
    public static final String EXTRA_ANONYMOUS_MODE = "AnonymousMode";
    public static final String EXTRA_USER = "User";

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
        final UserViewAdapter adapter = new UserViewAdapter(this,R.layout.layout_user_template,getUsers());
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onUserLogin(adapter.getItem(position));
            }
        });
    }

    private ArrayList<User> getUsers() {
        ArrayList<User> uList = new ArrayList<>();

        User user = new User("User 01",ActivityNewUser.USER_PICTURES[0]);
        uList.add(user);
        user = new User("User 02",ActivityNewUser.USER_PICTURES[1]);
        uList.add(user);
        user = new User("User 03",ActivityNewUser.USER_PICTURES[2]);
        uList.add(user);
        user = new User("User 04",ActivityNewUser.USER_PICTURES[3]);
        uList.add(user);

        return uList;
    }

    public void onUserNew(View v) {
        Intent intent = new Intent(this, ActivityNewUser.class);
        startActivity(intent);
    }

    public void onUserLogin(User u) {
        Intent intent = new Intent(this, ActivityHome.class);
        intent.putExtra(EXTRA_ANONYMOUS_MODE,false);
        intent.putExtra(EXTRA_USER,u);
        startActivity(intent);
        super.finish();
    }

    public void onAnonymousLogin(View v) {
        Intent intent = new Intent(this, ActivityHome.class);
        intent.putExtra(EXTRA_ANONYMOUS_MODE,true);
        startActivity(intent);
        super.finish();
    }
}
