package com.rogeri.schoolknowledge.controler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.rogeri.schoolknowledge.R;
import com.rogeri.schoolknowledge.data.DAOUser;
import com.rogeri.schoolknowledge.model.User;
import com.rogeri.schoolknowledge.view.UserViewAdapter;

public class ActivityLogin extends AppCompatActivity {

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
        DAOUser userDAO = new DAOUser();
        final UserViewAdapter adapter = new UserViewAdapter(this,R.layout.layout_user_template,userDAO.getJoueurs());
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
        startActivity(intent);
    }

    public void onUserLogin(User u) {
        Intent intent = new Intent(this, ActivityHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(ActivityHome.EXTRA_LOGIN_MODE, ActivityHome.LOGIN_MODE_USER);
        Log.d("--- LOGIN --- DEBUG ---", "GET User(" + u.getID() + ")");
        intent.putExtra(ActivityHome.EXTRA_PLAYER_ID, u.getID());
        startActivity(intent);
    }

    public void onAnonymousLogin(View v) {
        Intent intent = new Intent(this, ActivityHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(ActivityHome.EXTRA_LOGIN_MODE, ActivityHome.LOGIN_MODE_ANONYMOUS);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Please login !", Toast.LENGTH_SHORT).show();
    }

}
