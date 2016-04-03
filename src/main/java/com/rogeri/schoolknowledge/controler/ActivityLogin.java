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
import android.app.Activity;
import android.content.Context;

import com.rogeri.schoolknowledge.R;
import com.rogeri.schoolknowledge.SchoolKnowledge;
import com.rogeri.schoolknowledge.data.DAOUser;
import com.rogeri.schoolknowledge.model.User;
import com.rogeri.schoolknowledge.view.UserViewAdapter;

public class ActivityLogin extends AppCompatActivity {
  private static final String LOG_TAG=SchoolKnowledge.LOG_TAG+"-Login";
  private static final int REQUEST_NEW_USER=30;

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
        DAOUser userDAO = new DAOUser(this);
        final UserViewAdapter adapter = new UserViewAdapter(this,R.layout.template_user_list,userDAO.selectAll());
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                onUserLogin(adapter.getItem(position));
            }
        });
    }

    public void onUserNew(View v) {
        Log.d(LOG_TAG,"Oppening new user.");
        Intent intent = new Intent(this, ActivityNewUser.class);
        startActivityForResult(intent,REQUEST_NEW_USER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      if (requestCode==REQUEST_NEW_USER) {
        if (resultCode==Activity.RESULT_OK) {
          Intent intent = new Intent(this, ActivityHome.class);
          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
          startActivity(intent);
        }
      }
      super.onActivityResult(resultCode, requestCode, data);
    }

    public void onUserLogin(User u) {
        Log.d(LOG_TAG,"Logging in with user "+u.getName());
        Intent intent = new Intent(this, ActivityHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ((SchoolKnowledge)getApplication()).setPlayer(u);
        startActivity(intent);
    }

    public void onAnonymousLogin(View v) {
      Log.d(LOG_TAG,"Logging in anonymously.");
        Intent intent = new Intent(this, ActivityHome.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        ((SchoolKnowledge)getApplication()).setAnonymous();
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "Please login !", Toast.LENGTH_SHORT).show();
    }

}
