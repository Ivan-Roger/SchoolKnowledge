package com.rogeri.schoolknowledge.controler;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.rogeri.schoolknowledge.R;
import com.rogeri.schoolknowledge.data.DAOUser;
import com.rogeri.schoolknowledge.model.User;
import com.rogeri.schoolknowledge.view.ImageViewIdentified;

public class ActivityNewUser extends AppCompatActivity {
    private User joueur = null;
    private int pickedImage;

    public static int[] USER_PICTURES = {
            R.mipmap.user_icon_1,
            R.mipmap.user_icon_2,
            R.mipmap.user_icon_3,
            R.mipmap.user_icon_4,
            R.mipmap.user_icon_5,
            R.mipmap.user_icon_6
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        fillImagePicker();
    }

    private void fillImagePicker() {
        LinearLayout table = (LinearLayout) findViewById(R.id.new_user_pic_table);

        int i=0;
        for(int rID: USER_PICTURES) {
            ImageViewIdentified view = new ImageViewIdentified(this,i);
            view.setImageResource(rID);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout table = (LinearLayout) findViewById(R.id.new_user_pic_table);
                    for (int j = 0; j < table.getChildCount(); j++) {
                        table.getChildAt(j).setAlpha((float) 1);
                    }
                    v.setAlpha((float) 0.5);
                    pickedImage = ((ImageViewIdentified) v).getIdentifier();
                }
            });
            table.addView(view);
        }
    }

    public void onSubmit(View v) {
        EditText name = (EditText) findViewById(R.id.new_user_name_input);
        if (name.getText().toString().isEmpty())
            Toast.makeText(this, "@string/wrong_username", Toast.LENGTH_SHORT).show();
        else {
            DAOUser userDAO = new DAOUser();
            User u = new User(User.getNextID(),name.getText().toString(), pickedImage);
            userDAO.addJoueur(u);
            super.finish();
        }
    }
}
